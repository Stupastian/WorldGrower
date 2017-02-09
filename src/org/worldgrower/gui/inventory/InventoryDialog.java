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
package org.worldgrower.gui.inventory;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

import org.worldgrower.WorldObject;
import org.worldgrower.actions.Actions;
import org.worldgrower.attribute.ManagedProperty;
import org.worldgrower.attribute.Prices;
import org.worldgrower.attribute.PropertyCountMap;
import org.worldgrower.attribute.WorldObjectContainer;
import org.worldgrower.gui.AbstractDialog;
import org.worldgrower.gui.ColorPalette;
import org.worldgrower.gui.ImageIds;
import org.worldgrower.gui.ImageInfoReader;
import org.worldgrower.gui.SwingUtils;
import org.worldgrower.gui.TiledImagePanel;
import org.worldgrower.gui.font.Fonts;
import org.worldgrower.gui.knowledge.ImageCellRenderer;
import org.worldgrower.gui.music.SoundIdReader;
import org.worldgrower.gui.util.DialogUtils;
import org.worldgrower.gui.util.JButtonFactory;
import org.worldgrower.gui.util.JLabelFactory;
import org.worldgrower.gui.util.JPanelFactory;
import org.worldgrower.gui.util.JTableFactory;
import org.worldgrower.gui.util.MenuFactory;

public final class InventoryDialog extends AbstractDialog {

	private static final String MONEY_PLAYER_CHARACTER_TOOL_TIP = "shows amount of gold that the player character has";
	private static final String WEIGHT_PLAYER_CHARACTER_TOOL_TIP = "shows current weight of things that the player character is carrying and maximum weight";
	private static final String MONEY_TARGET_TOOL_TIP = "shows amount of gold that a character has";
	private static final String WEIGHT_TARGET_TOOL_TIP = "shows current weight of things that a character is carrying and maximum weight";
	private static final String DEMANDS_TOOL_TIP = "show list of items that other npcs may sell to the player character";
	private static final String PRICES_TOOL_TIP = "show list of items with associated prices. These prices are used instead of the default prices when an item is sold by the player character";
	
	private final ImageInfoReader imageInfoReader;
	private final SoundIdReader soundIdReader;
	
	private JPanel rootInventoryPanel;
	
	private JPanel inventoryPanel;
	private JTable inventoryTable;
	private JButton okButton;

	private JLabel moneyValueLabel;
	private JLabel weightLabelValue;

	private JPanel targetInventoryPanel;
	private JTable targetInventoryTable;
	private JLabel targetMoney;
	private JLabel targetWeight;
	
	private JButton demandsButton;
	private JButton pricesButton;
	
	private JPanel containersPanel;
	private JToggleButton playercharacterToggleButton;
	private JToggleButton targetToggleButton;
	private JButton stealMoneyButton;
	
	private final class CloseDialogAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			InventoryDialog.this.dispose();
		}
	}
	
	public InventoryDialog(InventoryDialogModel inventoryDialogModel, ImageInfoReader imageInfoReader, SoundIdReader soundIdReader, InventoryActionFactory inventoryActionFactory, JFrame parentFrame) {
		super(762, 710, imageInfoReader);
		this.imageInfoReader = imageInfoReader;
		this.soundIdReader = soundIdReader;
		
		initializeGUI(inventoryDialogModel, imageInfoReader, inventoryActionFactory, parentFrame);
	}

	private void initializeGUI(InventoryDialogModel inventoryDialogModel, ImageInfoReader imageInfoReader, InventoryActionFactory inventoryActionFactory, JFrame parentFrame) {
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction(new CloseDialogAction(), stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
			
		JPanel buttonPane = new JPanel();
		buttonPane.setOpaque(false);
		buttonPane.setBounds(378, 655, 374, 40);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		addComponent(buttonPane);

		okButton = JButtonFactory.createButton("Ok", imageInfoReader, soundIdReader);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		okButton.addActionListener(new CloseDialogAction());

		rootInventoryPanel = JPanelFactory.createBorderlessPanel();
		rootInventoryPanel.setBounds(12, 12, 700, 600);
		CardLayout cardLayout = new CardLayout();
		rootInventoryPanel.setLayout(cardLayout);
		addComponent(rootInventoryPanel);
		
		inventoryPanel = new TiledImagePanel(imageInfoReader);
		inventoryPanel.setBounds(0, 0, 700, 600);
		inventoryPanel.setLayout(null);
		inventoryPanel.setOpaque(true);
		inventoryPanel.setBackground(ColorPalette.DARK_BACKGROUND_COLOR);
		rootInventoryPanel.add(inventoryPanel, "player");
		
		inventoryTable = createInventoryTable(inventoryDialogModel.getPlayerCharacterInventory(), imageInfoReader);
		
		addFilterPanel(inventoryPanel, inventoryTable);
		
		JScrollPane inventoryScrollPane = new JScrollPane();
		inventoryScrollPane.setViewportView(inventoryTable);
		inventoryScrollPane.setBounds(12, 62, 500, 530);
		inventoryScrollPane.getViewport().setBackground(ColorPalette.DARK_BACKGROUND_COLOR);
		SwingUtils.makeTransparant(inventoryTable, inventoryScrollPane);
		inventoryPanel.add(inventoryScrollPane);
		
		int labelLeft = 522;
		int labelValueLeft = 587;
		
		final JLabel moneyLabel = JLabelFactory.createJLabel("Money:");
		moneyLabel.setToolTipText(MONEY_PLAYER_CHARACTER_TOOL_TIP);
		moneyLabel.setBounds(labelLeft, 62, 64, 25);
		inventoryPanel.add(moneyLabel);
		
		Image smallCoinImage = imageInfoReader.getImage(ImageIds.SMALL_GOLD_COIN, null);
		moneyValueLabel = JLabelFactory.createJLabel(inventoryDialogModel.getPlayerCharacterMoney(), smallCoinImage);
		moneyValueLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		moneyValueLabel.setToolTipText(MONEY_PLAYER_CHARACTER_TOOL_TIP);
		moneyValueLabel.setBounds(labelValueLeft, 62, 100, 25);
		inventoryPanel.add(moneyValueLabel);
		
		JLabel lblWeight = JLabelFactory.createJLabel("Weight:");
		lblWeight.setToolTipText(WEIGHT_PLAYER_CHARACTER_TOOL_TIP);
		lblWeight.setBounds(labelLeft, 12, 64, 25);
		inventoryPanel.add(lblWeight);
		
		String weightString = getPlayerCharacterWeight(inventoryDialogModel);
		weightLabelValue = JLabelFactory.createJLabel(weightString);
		weightLabelValue.setToolTipText(WEIGHT_PLAYER_CHARACTER_TOOL_TIP);
		weightLabelValue.setBounds(labelValueLeft, 12, 75, 25);
		inventoryPanel.add(weightLabelValue);
		
		demandsButton = JButtonFactory.createButton("Set buying items", imageInfoReader, soundIdReader);
		demandsButton.setToolTipText(DEMANDS_TOOL_TIP);
		demandsButton.setIcon(new ImageIcon(imageInfoReader.getImage(ImageIds.SILVER_COIN, null)));
		demandsButton.setBounds(labelLeft, 480, 175, 50);
		inventoryPanel.add(demandsButton);
		
		pricesButton = JButtonFactory.createButton("Set selling prices", imageInfoReader, soundIdReader);
		pricesButton.setToolTipText(PRICES_TOOL_TIP);
		pricesButton.setIcon(new ImageIcon(imageInfoReader.getImage(ImageIds.GOLD_COIN, null)));
		pricesButton.setBounds(labelLeft, 541, 175, 50);
		inventoryPanel.add(pricesButton);

		if (inventoryDialogModel.hasTarget()) {
			targetInventoryPanel = new TiledImagePanel(imageInfoReader);
			targetInventoryPanel.setLayout(null);
			targetInventoryPanel.setBounds(0, 0, 700, 600);
			targetInventoryPanel.setOpaque(true);
			targetInventoryPanel.setBackground(ColorPalette.DARK_BACKGROUND_COLOR);
			rootInventoryPanel.add(targetInventoryPanel, "target");
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(12, 62, 500, 530);
			scrollPane.getViewport().setBackground(ColorPalette.DARK_BACKGROUND_COLOR);
			targetInventoryPanel.add(scrollPane);
			
			targetInventoryTable = createInventoryTable(inventoryDialogModel.getTargetInventory(), imageInfoReader);
			scrollPane.setViewportView(targetInventoryTable);
			SwingUtils.makeTransparant(targetInventoryTable, scrollPane);
			
			addFilterPanel(targetInventoryPanel, targetInventoryTable);
			
			if (inventoryDialogModel.hasTargetMoney()) {
				JLabel targetMoneyLabel = JLabelFactory.createJLabel("Money:");
				targetMoneyLabel.setToolTipText(MONEY_TARGET_TOOL_TIP);
				targetMoneyLabel.setBounds(labelLeft, 62, 64, 25);
				targetInventoryPanel.add(targetMoneyLabel);
				
				targetMoney = JLabelFactory.createJLabel(inventoryDialogModel.getTargetMoney(), smallCoinImage);
				targetMoney.setHorizontalTextPosition(SwingConstants.LEADING);
				targetMoney.setToolTipText(MONEY_TARGET_TOOL_TIP);
				targetMoney.setBounds(labelValueLeft, 62, 100, 25);
				targetInventoryPanel.add(targetMoney);
				
				Image stealGoldImage = imageInfoReader.getImage(Actions.STEAL_GOLD_ACTION.getImageIds(null), null);
				stealMoneyButton = JButtonFactory.createButton("Steal money", new ImageIcon(stealGoldImage), imageInfoReader, soundIdReader);
				stealMoneyButton.setToolTipText("steal money");
				stealMoneyButton.setBounds(labelLeft, 112, 150, 50);
				stealMoneyButton.setEnabled(inventoryActionFactory.hasTargetMoneyActions());
				stealMoneyButton.addActionListener(inventoryActionFactory.getTargetMoneyActions().get(0));
				targetInventoryPanel.add(stealMoneyButton);
			}
			
			if (inventoryDialogModel.hasTargetCarryingCapacity()) {
				JLabel targetWeightLabel = JLabelFactory.createJLabel("Weight:");
				targetWeightLabel.setToolTipText(WEIGHT_TARGET_TOOL_TIP);
				targetWeightLabel.setBounds(labelLeft, 12, 64, 25);
				targetInventoryPanel.add(targetWeightLabel);
				
				String targetWeightString = getTargetWeight(inventoryDialogModel);
				targetWeight = JLabelFactory.createJLabel(targetWeightString);
				targetWeight.setToolTipText(WEIGHT_TARGET_TOOL_TIP);
				targetWeight.setBounds(labelValueLeft, 12, 75, 25);
				targetInventoryPanel.add(targetWeight);
			}
			
			addContainerPanel(inventoryDialogModel, imageInfoReader);
			
			setPlayerCharacterPanelOnTop(null);
		}
		
		setInventoryActions(inventoryDialogModel::setPrices, inventoryDialogModel.getPlayerCharacterDemands(), inventoryDialogModel.getPlayerCharacterPrices());
		addPopupMenuToInventoryList(inventoryDialogModel, inventoryActionFactory);
		
		DialogUtils.createDialogBackPanel(this, parentFrame.getContentPane());
	}

	private void addContainerPanel(InventoryDialogModel inventoryDialogModel, ImageInfoReader imageInfoReader) {
		containersPanel = JPanelFactory.createBorderlessPanel();
		containersPanel.setLayout(null);
		containersPanel.setBounds(24, 615, 500, 50);
		addComponent(containersPanel);
		
		playercharacterToggleButton = JButtonFactory.createToggleButton(inventoryDialogModel.getPlayerCharacterName(), new ImageIcon(inventoryDialogModel.getPlayerCharacterImage(imageInfoReader)), soundIdReader);
		playercharacterToggleButton.setToolTipText(inventoryDialogModel.getPlayerCharacterName());
		playercharacterToggleButton.setBounds(0, 0, 250, 50);
		playercharacterToggleButton.setOpaque(false);
		playercharacterToggleButton.addActionListener(this::setPlayerCharacterPanelOnTop);
		containersPanel.add(playercharacterToggleButton);
		
		targetToggleButton = JButtonFactory.createToggleButton(inventoryDialogModel.getTargetName(), new ImageIcon(inventoryDialogModel.getTargetImage(imageInfoReader)), soundIdReader);
		targetToggleButton.setToolTipText(inventoryDialogModel.getTargetName());
		targetToggleButton.setBounds(250, 0, 250, 50);
		targetToggleButton.setOpaque(false);
		targetToggleButton.addActionListener(this::setTargetInventoryOnTop);
		containersPanel.add(targetToggleButton);
	}

	private void addFilterPanel(JPanel parentPanel, JTable parentTable) {
		JPanel filterPanel = JPanelFactory.createBorderlessPanel();
		filterPanel.setBounds(12, 12, 10 * 50, 50);
		filterPanel.setLayout(null);
		filterPanel.setBackground(ColorPalette.DARK_BACKGROUND_COLOR);
		parentPanel.add(filterPanel);
		
		OptionalTableColumn attackOptionalTableColumn = new OptionalTableColumn(0, "Attack", i -> i.getAttack());
		OptionalTableColumn armorOptionalTableColumn = new OptionalTableColumn(0, "Armor", i -> i.getArmor());
		OptionalTableColumn toolsOptionalTableColumn = new OptionalTableColumn(0, "Bonus", i -> i.getToolBonus());
		OptionalTableColumn priceOptionalTableColumn = new OptionalTableColumn(0, "Price", i -> Integer.toString(i.getPrice()), ImageIds.SMALL_GOLD_COIN);
		
		List<JToggleButton> filterButtons = new ArrayList<>();
		filterButtons.add(createFilterButton(filterPanel, 0, ImageIds.CHEST, "Show all items", parentTable, priceOptionalTableColumn));
		filterButtons.add(createFilterButton(filterPanel, 1, ImageIds.IRON_CLAYMORE, "Show weapons", parentTable, attackOptionalTableColumn));
		filterButtons.add(createFilterButton(filterPanel, 2, ImageIds.IRON_CUIRASS, "Show armor", parentTable, armorOptionalTableColumn));
		filterButtons.add(createFilterButton(filterPanel, 3, ImageIds.SLEEPING_POTION, "Show drinks and potions", parentTable));
		filterButtons.add(createFilterButton(filterPanel, 4, ImageIds.BERRY, "Show food", parentTable));
		filterButtons.add(createFilterButton(filterPanel, 5, ImageIds.NIGHT_SHADE, "Show ingredients", parentTable));
		filterButtons.add(createFilterButton(filterPanel, 6, ImageIds.SPELL_BOOK, "Show books", parentTable));
		filterButtons.add(createFilterButton(filterPanel, 7, ImageIds.KEY, "Show keys", parentTable));
		filterButtons.add(createFilterButton(filterPanel, 8, ImageIds.WOOD, "Show resources", parentTable));
		filterButtons.add(createFilterButton(filterPanel, 9, ImageIds.SCYTHE, "Show tools", parentTable, toolsOptionalTableColumn));
		
		List<RowFilter<InventoryModel, Integer>> rowFilters = createRowFilters();
		ButtonGroup buttonGroup = new ButtonGroup();
		int index = 0;
		for(JToggleButton toggleButton : filterButtons) {
			buttonGroup.add(toggleButton);
			final int i = index;
			toggleButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					TableRowSorter<InventoryModel> tableRowSorter = (TableRowSorter<InventoryModel>)parentTable.getRowSorter();
					tableRowSorter.setRowFilter(rowFilters.get(i));
				}
			});
			index++;
		}
		
		filterButtons.get(0).setSelected(true);
	}
	
	
	
	private List<RowFilter<InventoryModel, Integer>> createRowFilters() {
		return Arrays.asList(new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return true;
			}
		},
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isWeapon();
			}
		},
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isArmor();
			}
		},
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isPotion();
			}
		},
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isFood();
			}
		}
		,
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isAlchemyIngredient();
			}
		}
		,
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isBook();
			}
		}
		,
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isKey();
			}
		}
		,
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isResource();
			}
		}
		,
		new RowFilter<InventoryModel, Integer>() {
			public boolean include(Entry<? extends InventoryModel, ? extends Integer> entry) {
				return entry.getModel().getInventoryItem(entry.getIdentifier()).isTool();
			}
		}
		);
	}
	
	private JToggleButton createFilterButton(JPanel filterPanel, int index, ImageIds imageId, String tooltipText, JTable parentTable, OptionalTableColumn... optionalTableColumns) {
		JToggleButton filterToggleButton = JButtonFactory.createToggleButton(new ImageIcon(imageInfoReader.getImage(imageId, null)), soundIdReader);
		filterToggleButton.setOpaque(false);
		filterToggleButton.setBounds(index * 50, 0, 50, 50);
		filterToggleButton.setToolTipText(tooltipText);
		
		filterToggleButton.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				InventoryModel inventoryModel = (InventoryModel)parentTable.getModel();
				JTableHeader th = parentTable.getTableHeader();
				TableColumnModel tcm = th.getColumnModel();
				if (e.getStateChange() == ItemEvent.SELECTED) {
					for(OptionalTableColumn optionalTableColumn : optionalTableColumns) {
						TableColumn tableColumn = new TableColumn(tcm.getColumnCount());
						tableColumn.setHeaderValue(optionalTableColumn.getName());
						if (optionalTableColumn.getImageId() != null) {
							JTableFactory.applyImageToHeaderColumn(parentTable, tableColumn, optionalTableColumn.getImageId(), imageInfoReader);
						}
						
						tcm.addColumn(tableColumn);
						inventoryModel.addColumn(optionalTableColumn);
					}
					
					th.repaint();
					
				} else if (e.getStateChange() == ItemEvent.DESELECTED) {
					for(OptionalTableColumn optionalTableColumn : optionalTableColumns) {
						tcm.removeColumn(tcm.getColumn(tcm.getColumnCount() - 1));
						inventoryModel.removeColumn(optionalTableColumn);
						parentTable.getRowSorter().modelStructureChanged();
					}
					
					th.repaint();
				}
			}
		});
		
		
		filterPanel.add(filterToggleButton);
		return filterToggleButton;
	}
	
	private static class OptionalTableColumn {

		private final int id;
		private final String name;
		private final Function<InventoryItem, String> valueFunction;
		private final ImageIds imageId;
		
		public OptionalTableColumn(int id, String name, Function<InventoryItem, String> valueFunction) {
			this(id, name, valueFunction, null);
		}
		
		public OptionalTableColumn(int id, String name, Function<InventoryItem, String> valueFunction, ImageIds imageId) {
			super();
			this.id = id;
			this.name = name;
			this.valueFunction = valueFunction;
			this.imageId = imageId;
		}

		@Override
		public int hashCode() {
			return id;
		}

		@Override
		public boolean equals(Object obj) {
			return ((OptionalTableColumn)obj).id == id;
		}

		public String getName() {
			return name;
		}

		public String getValue(InventoryItem inventoryItem) {
			return valueFunction.apply(inventoryItem);
		}

		public ImageIds getImageId() {
			return imageId;
		}
	}

	private void setPlayerCharacterPanelOnTop(ActionEvent e) {
		CardLayout cardLayout = (CardLayout) rootInventoryPanel.getLayout();
		cardLayout.show(rootInventoryPanel, "player");
		playercharacterToggleButton.setFont(Fonts.FONT);
		playercharacterToggleButton.setSelected(true);
		targetToggleButton.setFont(Fonts.FONT);
		targetToggleButton.setSelected(false);
	}
	
	private void setTargetInventoryOnTop(ActionEvent e) {
		CardLayout cardLayout = (CardLayout) rootInventoryPanel.getLayout();
		cardLayout.show(rootInventoryPanel, "target");
		playercharacterToggleButton.setFont(Fonts.FONT);
		playercharacterToggleButton.setSelected(false);
		targetToggleButton.setFont(Fonts.FONT);
		targetToggleButton.setSelected(true);
	}

	private void selectCurrentRow(JTable table, MouseEvent e) {
		int row = table.rowAtPoint(e.getPoint());
		table.setRowSelectionInterval(row, row);
	}
	
	private void addPopupMenuToInventoryList(InventoryDialogModel inventoryDialogModel, InventoryActionFactory inventoryActionFactory) {
		inventoryTable.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				selectCurrentRow(inventoryTable, e);
		        InventoryItem inventoryItem = getPlayerCharacterSelectedValue();
		        
		        if (inventoryItem != null) {
			        JPopupMenu popupMenu = MenuFactory.createJPopupMenu(imageInfoReader);
					addPlayerCharacterMenuActions(popupMenu, inventoryItem, inventoryDialogModel, inventoryActionFactory);
			        popupMenu.show(inventoryTable, e.getX(), e.getY());
		        }
			}
		});
		
		if (targetInventoryTable != null) {
			targetInventoryTable.addMouseListener(new MouseAdapter() {

				@Override
				public void mousePressed(MouseEvent e) {
					selectCurrentRow(targetInventoryTable, e);
			        InventoryItem inventoryItem = getTargetSelectedValue();
			        
			        if (inventoryItem != null) {
				        JPopupMenu popupMenu = MenuFactory.createJPopupMenu(imageInfoReader);
						addTargetMenuActions(popupMenu, inventoryItem, inventoryDialogModel, inventoryActionFactory);
				        popupMenu.show(targetInventoryTable, e.getX(), e.getY());
			        }
				}
			});
		}
	}
	
	private String getTargetWeight(InventoryDialogModel inventoryDialogModel) {
		String targetWeightString = Integer.toString(inventoryDialogModel.getTargetWeight())
				+ "/"
				+Integer.toString(inventoryDialogModel.getTargetCarryingCapacity());
		return targetWeightString;
	}

	private String getPlayerCharacterWeight(InventoryDialogModel inventoryDialogModel) {
		String weightString = Integer.toString(inventoryDialogModel.getPlayerCharacterWeight())
							+ "/"
							+Integer.toString(inventoryDialogModel.getPlayerCharacterCarryingCapacity());
		return weightString;
	}

	private void setInventoryActions(Consumer<int[]> setPricesAction, PropertyCountMap<ManagedProperty<?>> demands, Prices pricesOnPlayer) {
		demandsButton.addActionListener(e -> new DemandsDialog(demands, imageInfoReader, soundIdReader).showMe());
		pricesButton.addActionListener(e -> new PricesDialog(setPricesAction, pricesOnPlayer, imageInfoReader, soundIdReader).showMe());
	}

	private void addPlayerCharacterMenuActions(JPopupMenu popupMenu, InventoryItem inventoryItem, InventoryDialogModel inventoryDialogModel, InventoryActionFactory inventoryActionFactory) {
		for(Action inventoryDialogAction : inventoryActionFactory.getPlayerCharacterInventoryActions(inventoryItem.getId())) {
			JMenuItem actionMenuItem = MenuFactory.createJMenuItem(inventoryDialogAction, soundIdReader);
			actionMenuItem.setToolTipText((String) inventoryDialogAction.getValue(Action.LONG_DESCRIPTION));
			popupMenu.add(actionMenuItem);
		}
	}
	
	private void addTargetMenuActions(JPopupMenu popupMenu, InventoryItem inventoryItem, InventoryDialogModel inventoryDialogModel, InventoryActionFactory inventoryActionFactory) {
		for(Action inventoryDialogAction : inventoryActionFactory.getTargetInventoryActions(inventoryItem.getId())) {
			JMenuItem actionMenuItem = MenuFactory.createJMenuItem(inventoryDialogAction, soundIdReader);
			actionMenuItem.setToolTipText((String) inventoryDialogAction.getValue(Action.LONG_DESCRIPTION));
			popupMenu.add(actionMenuItem);
		}
	}

	private JTable createInventoryTable(WorldObjectContainer inventory, ImageInfoReader imageInfoReader) {
		JTable inventoryTable = JTableFactory.createJTable(new InventoryModel(inventory));
		
		inventoryTable.setDefaultRenderer(ImageIds.class, new InventoryItemImageRenderer(imageInfoReader));
		inventoryTable.getColumnModel().getColumn(1).setCellRenderer(new InventoryItemRenderer());
		inventoryTable.getColumnModel().getColumn(2).setCellRenderer(new InventoryItemRenderer());
		inventoryTable.getColumnModel().getColumn(3).setCellRenderer(new InventoryItemRenderer());
		
		
		inventoryTable.setRowHeight(50);
		inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		inventoryTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		inventoryTable.getColumnModel().getColumn(1).setPreferredWidth(245);
		inventoryTable.getColumnModel().getColumn(2).setPreferredWidth(66);
		inventoryTable.getColumnModel().getColumn(3).setPreferredWidth(61);
		inventoryTable.getTableHeader().setReorderingAllowed(false);
		
		inventoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inventoryTable.setAutoCreateRowSorter(true);
		
		return inventoryTable;
	}
	
	private static class InventoryItemRenderer extends DefaultTableCellRenderer {
		public InventoryItemRenderer() {
			super();
			setOpaque(false);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setTooltip(table, row, component);
			return component;
		}
	}
	
	private static class InventoryItemImageRenderer extends ImageCellRenderer {

		public InventoryItemImageRenderer(ImageInfoReader imageInfoReader) {
			super(imageInfoReader);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JLabel component = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			setTooltip(table, row, component);
			return component;
		}
	}
	
	private static void setTooltip(JTable table, int row, JLabel component) {
		int rowIndex = table.getRowSorter().convertRowIndexToModel(row);
		InventoryItem inventoryItem = ((InventoryModel)table.getModel()).getInventoryItem(rowIndex);
		String tooltip = inventoryItem.getLongDescription();
		component.setToolTipText(tooltip);
	}	
	
	private static List<InventoryItem> getInventoryList(WorldObjectContainer inventory) {
		List<InventoryItem> inventoryList = new ArrayList<>();
		for(int index=0; index < inventory.size(); index++) {
			WorldObject inventoryItem = inventory.get(index);
			if (inventoryItem != null) {
				inventoryList.add(new InventoryItem(index, inventoryItem));
			}
		}
		return inventoryList;
	}
	
	public void showMe() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void refresh(InventoryDialogModel inventoryDialogModel) {
		((InventoryModel)inventoryTable.getModel()).refresh(inventoryDialogModel.getPlayerCharacterInventory());
		moneyValueLabel.setText(Integer.toString(inventoryDialogModel.getPlayerCharacterMoney()));
		String weightString = getPlayerCharacterWeight(inventoryDialogModel);
		weightLabelValue.setText(weightString);
		
		if (inventoryDialogModel.hasTarget()) {
			((InventoryModel)targetInventoryTable.getModel()).refresh(inventoryDialogModel.getTargetInventory());
			targetInventoryTable.repaint();
			
			if (inventoryDialogModel.hasTargetMoney()) {
				targetMoney.setText(Integer.toString(inventoryDialogModel.getTargetMoney()));
				stealMoneyButton.setEnabled(inventoryDialogModel.targetHasMoneyToSteal());
			}
			if (inventoryDialogModel.hasTargetCarryingCapacity()) {
				String targetWeightString = getTargetWeight(inventoryDialogModel);
				targetWeight.setText(targetWeightString);
			}
		}
	}

	public InventoryItem getPlayerCharacterSelectedValue() {
		return getSelectedValue(inventoryTable);
	}

	private InventoryItem getSelectedValue(JTable inventoryTable) {
		int selectedRow = inventoryTable.convertRowIndexToModel(inventoryTable.getSelectedRow());
		InventoryModel inventoryModel = (InventoryModel) inventoryTable.getModel();
		return inventoryModel.getInventoryItem(selectedRow);
	}
	
	public InventoryItem getTargetSelectedValue() {
		return getSelectedValue(targetInventoryTable);
	}
	
	private static class InventoryModel extends AbstractTableModel {

		private final List<InventoryItem> inventoryItems;
		private final List<OptionalTableColumn> additionalColumns = new ArrayList<>();
		
		public InventoryModel(WorldObjectContainer inventory) {
			inventoryItems = getInventoryList(inventory);
		}
		
		public void addColumn(OptionalTableColumn optionalTableColumn) {
			additionalColumns.add(optionalTableColumn);
		}
		
		public void removeColumn(OptionalTableColumn optionalTableColumn) {
			additionalColumns.remove(optionalTableColumn);
		}

		public void refresh(WorldObjectContainer inventory) {
			inventoryItems.clear();
			inventoryItems.addAll(getInventoryList(inventory));
			
			this.fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return inventoryItems.size();
		}

		@Override
		public int getColumnCount() {
			return 4 + additionalColumns.size();
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex == 0) {
				return "Image";
			} else if (columnIndex == 1) {
				return "Description";
			} else if (columnIndex == 2) {
				return "Sellable";
			} else if (columnIndex == 3) {
				return "Weight";
			} else if (columnIndex >= 4) {
				return additionalColumns.get(columnIndex - 4).getName();
			} else {
				return null;
			}
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if (columnIndex == 0) {
				return ImageIds.class;
			} else if (columnIndex == 2) {
				return Boolean.class;
			}
			return super.getColumnClass(columnIndex);
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return inventoryItems.get(rowIndex).getImageId();
			} else if (columnIndex == 1) {
				String description = inventoryItems.get(rowIndex).getDescription();
				int quantity = inventoryItems.get(rowIndex).getQuantity();
				if (quantity == 1) {
					return description;
				} else {
					return description + "(" + quantity + ")";
				}
			} else if (columnIndex == 2) {
				return inventoryItems.get(rowIndex).isSellable();
			} else if (columnIndex == 3) {
				return inventoryItems.get(rowIndex).getWeight();
			} else if (columnIndex >= 4) {
				return additionalColumns.get(columnIndex - 4).getValue(inventoryItems.get(rowIndex));
			}
			return null;
		}
		
		public InventoryItem getInventoryItem(int index) {
			return inventoryItems.get(index);
		}
	}
}