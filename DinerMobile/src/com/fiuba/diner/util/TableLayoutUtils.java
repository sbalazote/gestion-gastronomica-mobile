package com.fiuba.diner.util;

import com.fiuba.diner.helper.DataHolder;
import com.fiuba.diner.model.Floor;
import com.fiuba.diner.model.TableLayout;

public class TableLayoutUtils {

	private TableLayoutUtils() {
	}

	public static TableLayout[] convertLayout(Floor floor) {
		TableLayout[] convertedLayout = new TableLayout[floor.getLength() * floor.getWidth()];
		for (TableLayout layout : floor.getTableLayout()) {
			Integer position = layout.getRow() * floor.getWidth() + layout.getColumn();
			convertedLayout[position] = layout;

			// Esto es para que el objeto table de la lista de mesas sea el mismo que el de la distribucion fisica
			Integer index = DataHolder.getTables().indexOf(layout.getTable());
			layout.setTable(DataHolder.getTables().get(index));
		}
		return convertedLayout;
	}

}
