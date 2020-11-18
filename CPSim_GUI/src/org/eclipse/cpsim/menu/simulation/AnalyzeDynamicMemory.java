package org.eclipse.cpsim.menu.simulation;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
class DynamicChart extends JPanel {
	private XYSeriesCollection dataset;
	private XYSeries series;
	private XYSeries max_series;
	private final JFreeChart chart;
	private final String graph_title = "Analyze Dynamic Memory";
	private final String graph_x_axis = "Time (sec)";
	private final String graph_y_axis = "Memory (Bytes)";
	private final String graph_name = "name";
	private long starttime;
	private boolean changeaxis;
	private ValueAxis axis;
	private boolean setinitial = false;
	private final float[] initialvalue = { 0, 1 };

	public DynamicChart() {
		super();
		changeaxis = true;
		dataset = new XYSeriesCollection();
		series = new XYSeries(graph_name);
		max_series = new XYSeries("Maximum Value");
		dataset.addSeries(series);
		dataset.addSeries(max_series);

		chart = ChartFactory.createXYLineChart(graph_title, graph_x_axis, graph_y_axis, dataset);

		final XYPlot plot = chart.getXYPlot();

		ValueAxis xaxis = plot.getDomainAxis();
		axis = xaxis;
		axis.setLowerBound(0);
		axis.setUpperBound(10);
		ValueAxis yaxis = plot.getRangeAxis();
		yaxis.setLowerBound(0);
		yaxis.setAutoRange(true);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPopupMenu(null);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setMouseZoomable(false);
		add(chartPanel);
		starttime = System.currentTimeMillis();

		if (setinitial)
			series.add(initialvalue[0], initialvalue[1]);

		max_series.add(0, series.getMaxY());
		max_series.add(10, series.getMaxY());
		max_series.add(series.getMaxX(), series.getMaxY());
		update_maximum();
	}

	/* domain: sec, value: Bytes */
	public void update(float domain, float value) {
		if (changeaxis && domain > 10) {
			axis.setAutoRange(true);
			axis.setFixedAutoRange(10);
			changeaxis = false;
		}
		series.add(domain, value);

		update_maximum();

	}

	public void update_maximum() {
		if (max_series.getMaxY() < series.getMaxY()) {
			max_series.clear();
			max_series.add(0, series.getMaxY());
			max_series.add(10, series.getMaxY());
			max_series.add(series.getMaxX(), series.getMaxY());

		} else {
			max_series.add(series.getMaxX(), series.getMaxY());
		}
	}

}

public class AnalyzeDynamicMemory extends AbstractHandler {

	DynamicChart dcs;
	public static Thread thr = null;
	private boolean Running;
	private final String target_num = "500";
	public static boolean Open = false;
	public static JFrame frame = null;

	public AnalyzeDynamicMemory() {
		super();
	}

	public static void JFrame_exit() {
		if (frame != null)
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame = null;
		ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeDynamicMemory");
		org.eclipse.core.commands.State state = command.getState("org.eclipse.ui.commands.toggleState");
		state.setValue(false);
	}

	public static void JFrame_only_close() {
		if (frame != null)
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame = null;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeDynamicMemory");
		State state = command.getState("org.eclipse.ui.commands.toggleState");
		// System.out.println("Open: "+Open);

		if (!Open) {
			return null;
		}

		// System.out.println("Dynamic Memory: "+state.getValue());
		if (!(boolean) state.getValue())
			return null;

		if (Running)
			return null;

		Running = true;

		thr = new Thread() {
			private boolean IsIndex(byte[] inp, int index) {
				if (index < 4)
					return false;

				for (int i = 0; i < index - 1; i++) {
					if (inp[i] >= '0' && inp[i] <= '9')
						continue;
					return false;
				}
				return true;
			}

			@Override
			public void run() {
				try {
					System.out.println("Waiting...");
					Socket socket = new Socket("127.0.0.1", 7707);
					InputStream in = socket.getInputStream();
					int read = 0;
					byte[] buf = new byte[1024];

					String line = null;
					String[] sub_line = null;
					String[] tmp = null;
					int i = 0;
					long starttime = System.currentTimeMillis();
					String sub;

					while (Running) {
						if ((read = in.read(buf)) > 0) {
							line = new String(buf, 0, read).trim();
							sub_line = line.split("\t");
							for (int j = 0; j < sub_line.length; j++) {
								sub = sub_line[j];
								tmp = sub.split(" ");
								if (tmp[0].trim().equals(target_num)) {
									dcs.update((System.currentTimeMillis() - starttime) / 1000.f + 0.000001f * j,
											Float.parseFloat(tmp[1]));

								} else if (tmp.length == 4 && tmp[1].trim().equals(target_num)) {
									dcs.update((System.currentTimeMillis() - starttime) / 1000.f + 0.000001f * j,
											Float.parseFloat(tmp[2]));

								}
							}
						}

						// System.out.println(line);
						// System.out.println("");
					}
					in.close();
					socket.close();
					System.out.println("Thread Ended");

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void interrupt() {
				System.out.println("Interrupted!");
				Running = false;
				super.interrupt();
				thr = null;
				frame = null;

				ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
				Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeDynamicMemory");
				org.eclipse.core.commands.State state = command.getState("org.eclipse.ui.commands.toggleState");
				state.setValue(false);

			}
		};
		frame = new JFrame("Analyze Dynamic Memory");
		frame.setResizable(false);
		URL iconURL = getClass().getResource(MESSAGES.CPSIM_ICON_PNG);
		ImageIcon icon = new ImageIcon(iconURL);
		frame.setIconImage(icon.getImage());

		DynamicChart dc = new DynamicChart();

		frame.getContentPane().add(dc, BorderLayout.CENTER);
		frame.setSize(600, 350);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("Graph Closed");
				if (thr != null) {
					thr.interrupt();
					thr = null;
				}
				frame = null;
			}
		});

		frame.pack();
		frame.setVisible(true);
		dcs = dc;

		if (thr != null)
			thr.start();

		return null;
	}
}
