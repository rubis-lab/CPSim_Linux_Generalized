package org.eclipse.cpsim.menu.simulation;

import java.util.Comparator;

public class RoIComparator implements Comparator<String> {

	@Override
	public int compare(String a, String b) {
		String[] al = a.replace("[", "").replace("]", "").split(":");
		String[] bl = b.replace("[", "").replace("]", "").split(":");

		if (Integer.parseInt(al[0]) != Integer.parseInt(bl[0]))
			return Integer.parseInt(al[0]) - Integer.parseInt(bl[0]);
		else if (Integer.parseInt(al[1]) != Integer.parseInt(bl[1]))
			return Integer.parseInt(al[1]) - Integer.parseInt(bl[1]);
		else if (al.length == 2)
			return -1;
		else if (bl.length == 2)
			return 1;
		else if (Integer.parseInt(al[2]) != Integer.parseInt(bl[2]))
			return Integer.parseInt(al[2]) - Integer.parseInt(bl[2]);
		else
			return 0;
	}

}
