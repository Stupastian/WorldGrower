/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.gui.knowledge;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import org.worldgrower.gui.AbstractDialog;
import org.worldgrower.gui.ActionContainingArgs;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.gui.ImageInfoReader;
import org.worldgrower.gui.SwingUtils;
import org.worldgrower.gui.music.SoundIdReader;
import org.worldgrower.gui.util.DialogUtils;
import org.worldgrower.gui.util.JButtonFactory;
import org.worldgrower.gui.util.JScrollPaneFactory;
import org.worldgrower.gui.util.JTableFactory;

public class ChooseKnowledgeDialog extends AbstractDialog {

	private JTable knowledgeTable;
	private JButton okButton;
	
	private ActionContainingArgs guiAction;

	public ChooseKnowledgeDialog(List<KnowledgeDescription> knowledgeDescriptions, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader, Component parent, ActionContainingArgs guiAction, JFrame parentFrame) {
		super(600, 600, imageInfoReader);
		initializeGui(parent, knowledgeDescriptions, imageInfoReader, soundIdReader, parentFrame);
		
		this.guiAction = guiAction;
		
		handleActions();
	}

	private void initializeGui(Component parent, List<KnowledgeDescription> knowledgeDescriptions, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader, JFrame parentFrame) {
		knowledgeTable = JTableFactory.createJTable(new KnowledgeModel(knowledgeDescriptions));
		knowledgeTable.setDefaultRenderer(ImageIds.class, new ImageCellRenderer(imageInfoReader));
		knowledgeTable.setDefaultRenderer(String.class, new DefaultTableCellRenderer());
		knowledgeTable.setRowHeight(50);
		knowledgeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		knowledgeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		knowledgeTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		knowledgeTable.getColumnModel().getColumn(2).setPreferredWidth(500);
		knowledgeTable.getColumnModel().getColumn(0).setHeaderValue(" ");
		knowledgeTable.getColumnModel().getColumn(1).setHeaderValue(" ");
		knowledgeTable.getColumnModel().getColumn(2).setHeaderValue(" ");
		knowledgeTable.getTableHeader().setReorderingAllowed(false);
		
		JScrollPane scrollPane = JScrollPaneFactory.createScrollPane(knowledgeTable);
		scrollPane.setBounds(5, 5, 585, 510);
		this.addComponent(scrollPane);
		SwingUtils.makeTransparant(knowledgeTable, scrollPane);
		
		JPanel buttonPane = new JPanel(new BorderLayout());
		buttonPane.setBounds(5, 540, 585, 40);
		buttonPane.setOpaque(false);
		this.addComponent(buttonPane);
		
		okButton = JButtonFactory.createButton("OK", imageInfoReader, soundIdReader);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton, BorderLayout.EAST);
		getRootPane().setDefaultButton(okButton);
		
		DialogUtils.createDialogBackPanel(this, parentFrame.getContentPane());
	}
	
	private static class KnowledgeModel extends AbstractTableModel {

		private final List<KnowledgeModelItem> knowledgeItems;
		
		public KnowledgeModel(List<KnowledgeDescription> knowledgeDescriptions) {
			super();
			this.knowledgeItems = new ArrayList<>();
			for(KnowledgeDescription knowledgeDescription : knowledgeDescriptions) {
				this.knowledgeItems.add(new KnowledgeModelItem(knowledgeDescription.getId(), knowledgeDescription.getDescription(), knowledgeDescription.getImageId(), false));
			}
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public int getRowCount() {
			return knowledgeItems.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			if (column == 0) {
				return knowledgeItems.get(row).getSelected();
			} else if (column == 1) {
				return knowledgeItems.get(row).getImageId();
			} else if (column == 2) {
				return knowledgeItems.get(row).getKnowledgeDescription();
			}
			return null;
		}
		
		

		@Override
		public Class<?> getColumnClass(int column) {
			if (column == 0) {
				return Boolean.class;
			} else if (column == 1) {
				return ImageIds.class;
			} else if (column == 2) {
				return String.class;
			}
			return super.getColumnClass(column);
		}
		
		

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 0;
		}
		
		

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				knowledgeItems.get(rowIndex).setSelected((Boolean)aValue);
			}
		}

		public int[] getArgs() {
			List<KnowledgeModelItem> selectedItems = knowledgeItems.stream().filter(k -> k.getSelected()).collect(Collectors.toList());
			
			int[] args = new int[selectedItems.size()];
			for(int i=0; i<selectedItems.size(); i++) {
				KnowledgeModelItem knowledgeModelItem = selectedItems.get(i);
				args[i] = knowledgeModelItem.getId();
			}
			
			
			return args;
		}
	}

	public void showMe() {
		this.setVisible(true);
	}
	

	private void handleActions() {
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				KnowledgeModel knowledgeModel = (KnowledgeModel) knowledgeTable.getModel();
				int[] args = knowledgeModel.getArgs();
				
				guiAction.setArgs(args);
				
				guiAction.actionPerformed(null);		
				ChooseKnowledgeDialog.this.dispose();
			}
		});
	}
}
