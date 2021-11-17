package org.eclipse.cpsim.menu.simulation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.State;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

class CpsDuration implements TimePeriod {
	long start;
	long end;

	public CpsDuration(long start, long end) {
		this.start = start;
		this.end = end;
	}

	public int compareTo(Object obj) {

		int result;
		long difference;

		// CASE 1 : Comparing to another Second object
		// -------------------------------------------
		if (obj instanceof Long) {
			Long ms = (Long) obj;
			difference = new Long(start) - ms;

			if (difference > 0) {
				result = 1;
			} else {
				if (difference < 0) {
					result = -1;
				} else {
					result = 0;
				}
			}
		}

		// CASE 2 : Comparing to another TimePeriod object
		// -----------------------------------------------
		else if (obj instanceof RegularTimePeriod) {
			RegularTimePeriod rtp = (RegularTimePeriod) obj;
			final long thisVal = this.start;
			final long anotherVal = rtp.getFirstMillisecond();
			result = (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
		}

		// CASE 3 : Comparing to a non-TimePeriod object
		// ---------------------------------------------
		else {
			// consider time periods to be ordered after general objects
			result = 1;
		}

		return result;
	}

	@Override
	public Date getEnd() {
		return new Date(end);
	}

	public Date getStart() {
		return new Date(start);
	}
}

class SchedulingViewer extends JPanel {

	/* log file name. have to fix AnalyzeSystemWideRuntimeBehavior.filename, too. */
	protected final String filename = System.getenv("CPSIM_PATH") + "/Log/" + "scheduling.log";
	private static final long serialVersionUID = 1L;

	double interval = 200.0;
	int nNodes = 0;

	JFreeChart jfreechart;
	JFreeChart jfreechart2;
	TaskSeriesCollection tsCollection = new TaskSeriesCollection();
	TaskSeries[] ts;
	String[] axises;

	DateAxis dateaxis;

	public SchedulingViewer() {

		super(new BorderLayout());

		BufferedReader axisReader;
		String value;

		try {
			axisReader = new BufferedReader(new FileReader(filename));

			while ((value = axisReader.readLine()) == null)
				;

			StringTokenizer st = new StringTokenizer(value, ",");
			if (st.countTokens() <= 0) {
				System.out.println("Error! internal.log has no informations about NODES");
			} else
				System.out.println(value);
			axisReader.close();

			nNodes = st.countTokens();

			axises = new String[nNodes];

			for (int i = 0; i < nNodes; ++i) {
				axises[i] = st.nextToken().trim();
			}

			Arrays.sort(axises);

			// Reverse order of axises
			/*
			 * axises[nNodes-i-1]; axises[nNodes-i-1] = temp; }
			 */
			/*
			 * for(int i=0; i<nNodes; ++i) System.out.print(axises[i] + " ");
			 */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		ts = new TaskSeries[nNodes];
		// Test
		ts2 = new TaskSeries[2];

		for (int i = 0; i < nNodes; ++i) {
			ts[i] = new TaskSeries(axises[i]);
			// Test
			if (i == 3 || i == 4)
				ts2[i - 3] = new TaskSeries(axises[i]);
		}

		for (int i = 0; i < nNodes; ++i)// nNodes-1; i>=0;--i)
		{
			ts[i].add(new Task("demo", new CpsDuration(0, 2000)));
			tsCollection.add(ts[i]);
			// Test
			if (i == 3 || i == 4) {
				ts2[i - 3].add(new Task("demo", new CpsDuration(0, 2000)));
				tsCollection2.add(ts2[i - 3]);
			}
		}

		jfreechart = ChartFactory.createXYBarChart("ECU Internals", "", false, "Time", new XYTaskDataset(tsCollection),
				PlotOrientation.HORIZONTAL, true, false, false);
		jfreechart.setBackgroundPaint(new Color(0xFF, 0xFF, 0xFF, 0));

		XYPlot localXYPlot = (XYPlot) jfreechart.getPlot();
		localXYPlot.setBackgroundAlpha((float) 0.0);

		SymbolAxis localSymbolAxis = new SymbolAxis("", axises);
		localSymbolAxis.setGridBandsVisible(true);
		localSymbolAxis.setInverted(true);
		localXYPlot.setDomainAxis(localSymbolAxis);
		XYBarRenderer localXYBarRenderer = (XYBarRenderer) localXYPlot.getRenderer();
		localXYBarRenderer.setUseYInterval(true);

		dateaxis = new DateAxis("Time");
		dateaxis.setDateFormatOverride(new SimpleDateFormat("mm:ss.SSS", Locale.getDefault()));
		localXYPlot.setRangeAxis(dateaxis);

		dateaxis.setUpperBound(dateaxis.getLowerBound() + interval);

		ChartUtilities.applyCurrentTheme(jfreechart);

		localSymbolAxis.setAxisLineVisible(true);
		localSymbolAxis.setVisible(false);

		ChartPanel chartPanel = new ChartPanel(jfreechart);
		chartPanel.setPopupMenu(null);
		chartPanel.setMouseWheelEnabled(false);
		chartPanel.setMouseZoomable(false);
		chartPanel.setBackground(new Color(0xFF, 0xFF, 0xFF, 0));
		chartPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4),
				BorderFactory.createLineBorder(Color.black)));
		add("Center", chartPanel);

	}

	private static XYDataset createDataset(String s, double d, RegularTimePeriod regulartimeperiod, int i) {
		TimeSeries timeseries = new TimeSeries(s, regulartimeperiod.getClass());
		RegularTimePeriod regulartimeperiod1 = regulartimeperiod;
		double d1 = d;
		for (int j = 0; j < i; j++) {
			timeseries.add(regulartimeperiod1, d1);
			regulartimeperiod1 = regulartimeperiod1.next();
			d1 *= 1.0D + (Math.random() - 0.495D) / 10D;
		}

		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
		timeseriescollection.addSeries(timeseries);
		return timeseriescollection;
	}

	public static boolean isStringLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public class DataGenerator extends Timer implements ActionListener {
		private static final long serialVersionUID = 1L;
		public BufferedReader dataReader;
		private String value;

		public DataGenerator(int interval) {
			super(interval, null);
			try {
				dataReader = new BufferedReader(new FileReader(filename));
				dataReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			addActionListener(this);
		}

		public void CloseFileReader() {
			try {
				dataReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		double bye = 65.0;
		double hi = 55.0;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				while ((value = dataReader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(value, ",");

					if (st.countTokens() < 3)
						continue;

					String[] tokens = new String[st.countTokens()];
					for (int i = 0; i < tokens.length; ++i) {
						tokens[i] = st.nextToken().trim();
					}

					if (!isStringLong(tokens[0]))
						continue;

					long time = Long.parseLong(tokens[0]);
					String nodename = tokens[1];
					boolean active = tokens[2].equals("1");

					TaskSeries ts = tsCollection.getSeries(nodename);

					if (ts == null)
						continue;

					if (active) {
						ts.add(new Task("data", new CpsDuration(time, time)));
					} else {
						Task task = ts.get(ts.getItemCount() - 1);
						CpsDuration duration = (CpsDuration) task.getDuration();
						duration.end = time;
						task.setDuration(duration);
					}

					// System.out.println(time-interval + " ~ " + time);
					dateaxis.setUpperBound(time);
					dateaxis.setLowerBound(time - interval);

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

}

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AnalyzeSystemWideRuntimeBehavior extends AbstractHandler {

	/* log file name. have to fix SchedulingViewer.filename, too. */
	protected final String filename = System.getenv("CPSIM_PATH") + "/Log/" + "scheduling.log";
	public static boolean Open = false;
	public static JFrame frame = null;

	public AnalyzeSystemWideRuntimeBehavior() {
		super();
	}

	public static void JFrame_exit() {
		if (frame != null)
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame = null;
		ICommandService service = (ICommandService) PlatformUI.getWorkbench().getService(ICommandService.class);
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeSystemWideRuntimeBehavior");
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
		Command command = service.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeSystemWideRuntimeBehavior");
		State state = command.getState("org.eclipse.ui.commands.toggleState");
		// System.out.println("Open: "+Open);

		if (!Open) {
			return null;
		}

		// System.out.println("System-Wide: "+state.getValue());
		if (!(boolean) state.getValue())
			return null;

		try {
			FileReader f = new FileReader(filename);
			f.close();

			final SchedulingViewer sviewer = new SchedulingViewer();
			final SchedulingViewer.DataGenerator dg = sviewer.new DataGenerator(20);

			frame = new JFrame("Analyze System-Wide Runtime Behavior");
			frame.getContentPane().add(sviewer, BorderLayout.CENTER);
			URL iconURL = getClass().getResource(MESSAGES.CPSIM_ICON_PNG);
			ImageIcon icon = new ImageIcon(iconURL);
			frame.setIconImage(icon.getImage());

			frame.setBounds(800, 0, 600, 350);
			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					try {
						dg.dataReader.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					dg.stop();
				}
			});

			frame.pack();
			frame.setVisible(true);

			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					ICommandService service = (ICommandService) PlatformUI.getWorkbench()
							.getService(ICommandService.class);
					Command command = service
							.getCommand("hyundaiConfiguratorGraphiti.commands.AnalyzeSystemWideRuntimeBehavior");
					org.eclipse.core.commands.State state = command.getState("org.eclipse.ui.commands.toggleState");
					state.setValue(false);
					frame = null;
				}
			});
			dg.start();

		} catch (IOException e) {
			MessageDialog.openWarning(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "ERROR",
					"This is no such file: " + filename);

		}
		return null;
	}
}