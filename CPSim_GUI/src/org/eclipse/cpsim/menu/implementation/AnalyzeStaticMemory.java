package org.eclipse.cpsim.menu.implementation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.cpsim.Diagram.util.CmdExecuter;
import org.eclipse.cpsim.Diagram.util.MESSAGES;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AnalyzeStaticMemory extends AbstractHandler {

	protected final String env_string = MESSAGES.CPSIM_PATH;
	protected final String folder_string = MESSAGES.CPSIM_FOLDER_TOOL;
	protected final String cmd_string = MESSAGES.CPSIM_SM_CMD;
	protected final String log_string = MESSAGES.CPSIM_SM_LOG;
	private String target = null;

	public String getCPSIMenv() {
		try {
			String line = System.getenv(env_string);

			// check there are several paths
			if (line.indexOf(";") < 0)
				return line.trim();

			line = line.substring(0, line.indexOf(";"));
			return line.trim();

		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);

		FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);
		dialog.setText("Analyze Static Memory");
		dialog.setFilterExtensions(new String[] { "*.*", "*.exe; *.elf" });
		dialog.setFilterNames(new String[] { "All Files(*.*)", "*.exe, *.elf" });
		dialog.setFilterIndex(1);
		target = dialog.open();
		if (target == null)
			return null;

		new Thread() {
			public List<Integer> parsePeLog(String path) {
				try {
					FileInputStream fis = new FileInputStream(path);
					BufferedReader br = new BufferedReader(new InputStreamReader(fis));

					/* Unit: K(byte) */
					int codeSize = Integer.parseInt(br.readLine()) / 1024;
					int dataSize = Integer.parseInt(br.readLine()) / 1024;
					int bssSize = Integer.parseInt(br.readLine()) / 1024;
					br.close();

					List<Integer> list = new ArrayList<Integer>();
					list.add(0, codeSize);
					list.add(1, dataSize);
					list.add(2, bssSize);
					return list;
				} catch (Exception e) {
				}
				return null;
			}

			public void run() {
				try {
					target = target.replace(File.separatorChar, '/');

					String cpsim_env = getCPSIMenv();
					String dir = cpsim_env + "\\" + folder_string;
					String smLogPath = dir + "\\" + log_string;

					String cmdPath = cpsim_env + "\\" + folder_string + "\\" + cmd_string;
					List<String> args = new ArrayList<String>();
					args.add(target);
					args.add(smLogPath);
					if (target.endsWith("exe"))
						args.add("1");
					CmdExecuter.execute(cmdPath, dir, args, false);

					Thread.sleep(1000);

					List<Integer> list = parsePeLog(smLogPath);
					if (list.size() < 3)
						return;
					int codeSize = list.get(0);
					int dataSize = list.get(1);
					int bssSize = list.get(2);

					URL iconURL = getClass().getResource(MESSAGES.CPSIM_ICON_PNG);
					ImageIcon icon = new ImageIcon(iconURL);
					InfoPopup infoPopup = new InfoPopup();
					infoPopup.setIconImage(icon.getImage());
					infoPopup.setInfo(codeSize, dataSize, bssSize);
					infoPopup.init();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();

		return null;
	}

	class InfoPopup extends JFrame {
		public final String TITLE = "Static Memory Analysis";
		private final int x = 5;
		private final int y = 1;
		private int romSize = 128;

		private int codeSize, dataSize, bssSize;
		private JPanel[][] panelHolder = new JPanel[x][y];

		InfoPopup() {
			setTitle(TITLE);
			setSize(150, 300);
			move(200, 200);
			setLayout(new GridLayout(5, 1));
			setResizable(false);
			for (int i = 0; i < x; i++) {
				for (int j = 0; j < y; j++) {
					panelHolder[i][j] = new JPanel();
					panelHolder[i][j].setVisible(false);
					add(panelHolder[i][j]);
				}
			}
		}

		public void init() {
			setVisible(true);
		}

		public void setInfo(int codeSize, int dataSize, int bssSize) {
			this.codeSize = codeSize;
			this.dataSize = dataSize;
			this.bssSize = bssSize;
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);

			int w = getWidth();
			int h = getHeight();
			double hh = h * 4 / 6 + 30;
			g.setColor(Color.BLACK);
			g.drawRect(20, h / 6, w * 3 / 4, (int) hh);
			int totSize = (int) hh;
			// from 6/h to hh
			float portion = Math.max(1, codeSize + dataSize + bssSize);
			float codePortion = codeSize / portion;
			float dataPortion = dataSize / portion;
			float bssPortion = bssSize / portion;
			int dCodeSize = Math.max(20, (int) (totSize * codePortion));
			int dDataSize = Math.max(20, (int) (totSize * dataPortion));
			int dBssSize = Math.max(20, (int) (totSize * bssPortion));
			int nTotSize = dCodeSize + dDataSize + dBssSize;
			if (nTotSize > totSize) {
				int gap = nTotSize - totSize;
				dCodeSize -= gap * codePortion;
				dDataSize -= gap * dataPortion;
				dBssSize -= gap * bssPortion;
			}

			g.setFont(new Font("default", Font.BOLD, 14));
			// Code
			g.setColor(Color.GRAY);
			g.fillRect(20, h / 6, w * 3 / 4, dCodeSize);
			g.setColor(Color.WHITE);
			g.drawString("Code: " + codeSize + "K", w / 2 - 40, h / 6 + 15);

			// Data
			g.setColor(Color.BLUE);
			g.fillRect(20, h / 6 + dCodeSize, w * 3 / 4, dDataSize);
			g.setColor(Color.WHITE);
			g.drawString("Data: " + dataSize + "K", w / 2 - 40, h / 6 + dCodeSize + 15);

			// BSS
			g.setColor(Color.ORANGE);
			g.fillRect(20, h / 6 + dCodeSize + dDataSize, w * 3 / 4, dBssSize);
			g.setColor(Color.WHITE);
			g.drawString("BSS: " + bssSize + "K", w / 2 - 40, h / 6 + dCodeSize + dDataSize + 15);
		}

	}
}