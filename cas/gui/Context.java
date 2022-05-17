package cas.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import cas.Shop;
import cas.inv.BasketBucket;
import cas.inv.Item;
import cas.user.Admin;
import cas.user.User;
import java.beans.VetoableChangeListener;
import java.beans.PropertyChangeEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

public class Context {

	private Shop shop;
	private JFrame frmComputerShop;
	private JTabbedPane tabbedPane;
	private JTable table;
	private Item selected_item = null;

	/**
	 * Launch the application.
	 */
	public static void start(Shop shop) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Context window = new Context(shop);
					window.frmComputerShop.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Context(Shop shop) {
		this.shop = shop;
		initialize();
	}

	static abstract class Fonts {
		public final static Font small = new Font("Segoe UI", Font.PLAIN, 12);
		public final static Font medium = new Font("Segoe UI", Font.PLAIN, 14);
		public final static Font large = new Font("Segoe UI", Font.PLAIN, 18);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frmComputerShop = new JFrame();
		frmComputerShop.setExtendedState(Frame.MAXIMIZED_BOTH);
		frmComputerShop.setTitle("Computer Shop");
		frmComputerShop.getContentPane().setBackground(Color.WHITE);
		frmComputerShop.getContentPane().setFont(Fonts.large);
		frmComputerShop.getContentPane().setLayout(new CardLayout(0, 0));

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(Fonts.small);
		frmComputerShop.getContentPane().add(tabbedPane, "name_228408317749500");

		JPanel panel_login = new JPanel();
		tabbedPane.addTab("Login", null, panel_login, null);
		tabbedPane.setSelectedIndex(0);
		ArrayList<String> user_strings = new ArrayList<String>();
		for (User user : shop.users.users()) {
			user_strings.add(user.getUsername() + " " + user.getName() + " - " + user.getClass().getSimpleName());
		}

		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(Fonts.medium);
		panel_login.setLayout(new GridLayout(10, 2, 0, 0));

		JComboBox comboBox = new JComboBox();
		comboBox.setFont(Fonts.medium);
		comboBox.setModel(new DefaultComboBoxModel(user_strings.toArray()));
		panel_login.add(comboBox);
		panel_login.add(btnLogin);

		JPanel panel_shop = new JPanel();
		tabbedPane.addTab("Shop", null, panel_shop, null);
		panel_shop.setLayout(new CardLayout(0, 0));

		JSplitPane panel_customer = new JSplitPane();
		panel_customer.setResizeWeight(0.5);
		panel_customer.setContinuousLayout(true);
		panel_shop.add(panel_customer, "name_278746467530300");

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setFont(Fonts.medium);
		panel_customer.setLeftComponent(scrollPane_1);

		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		JButton btnBasket_add = new JButton("Add to Basket");
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting())
					return;
				selected_item = shop.inventory.items.get(table.getValueAt(table.getSelectedRow(), 1));
				btnBasket_add.setEnabled(selected_item != null);
			}
		});
		table.setFont(Fonts.small);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(table);
		String[] column_names = new String[] {
				"Type", "Barcode", "Brand", "Colour", "Connectivity", "Type", "Language/Buttons", "Quantity", "Price"
		};
		ArrayList<Object[]> tbl_rows = new ArrayList<Object[]>(shop.inventory.items.size());
		for (Item item : shop.inventory.items.values()) {
			tbl_rows.add(item.display().toArray());
		}
		Object[][] tbl_rows_object_2d = (Object[][]) tbl_rows
				.toArray(new Object[tbl_rows.get(0).length][tbl_rows.size()]);
		table.setModel(new DefaultTableModel(tbl_rows_object_2d, column_names));

		JPanel panel_basket = new JPanel();
		panel_customer.setRightComponent(panel_basket);
		SpringLayout sl_panel_basket = new SpringLayout();
		panel_basket.setLayout(sl_panel_basket);

		JList list = new JList();
		DefaultListModel<BasketBucket> basket_model = new DefaultListModel<BasketBucket>();
		list.setModel(basket_model);
		sl_panel_basket.putConstraint(SpringLayout.SOUTH, list, -90, SpringLayout.SOUTH, panel_basket);
		list.setFont(Fonts.small);
		sl_panel_basket.putConstraint(SpringLayout.WEST, list, 0, SpringLayout.WEST, panel_basket);
		sl_panel_basket.putConstraint(SpringLayout.EAST, list, 0, SpringLayout.EAST, panel_basket);

		panel_basket.add(list);

		JButton btnBasket_clear = new JButton("Clear Basket");
		btnBasket_clear.setFont(Fonts.medium);
		sl_panel_basket.putConstraint(SpringLayout.WEST, btnBasket_clear, 0, SpringLayout.WEST, panel_basket);
		sl_panel_basket.putConstraint(SpringLayout.EAST, btnBasket_clear, 0, SpringLayout.EAST, panel_basket);
		panel_basket.add(btnBasket_clear);

		JButton btnBasket_buy = new JButton("Purchase");
		btnBasket_buy.setFont(Fonts.medium);
		sl_panel_basket.putConstraint(SpringLayout.NORTH, btnBasket_buy, 0, SpringLayout.SOUTH, btnBasket_clear);
		sl_panel_basket.putConstraint(SpringLayout.WEST, btnBasket_buy, 0, SpringLayout.WEST, panel_basket);
		sl_panel_basket.putConstraint(SpringLayout.EAST, btnBasket_buy, 0, SpringLayout.EAST, panel_basket);
		panel_basket.add(btnBasket_buy);

		JLabel lblNewLabel = new JLabel("Basket");
		lblNewLabel.setFont(Fonts.medium);
		sl_panel_basket.putConstraint(SpringLayout.NORTH, list, 0, SpringLayout.SOUTH, lblNewLabel);
		panel_basket.add(lblNewLabel);

		btnBasket_add.setEnabled(false);
		btnBasket_add.setFont(Fonts.medium);
		sl_panel_basket.putConstraint(SpringLayout.NORTH, btnBasket_add, 2, SpringLayout.SOUTH, list);
		sl_panel_basket.putConstraint(SpringLayout.NORTH, btnBasket_clear, 2, SpringLayout.SOUTH, btnBasket_add);
		sl_panel_basket.putConstraint(SpringLayout.WEST, btnBasket_add, 0, SpringLayout.WEST, panel_basket);
		sl_panel_basket.putConstraint(SpringLayout.EAST, btnBasket_add, 0, SpringLayout.EAST, panel_basket);
		panel_basket.add(btnBasket_add);

		btnBasket_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basket_model.removeAllElements();
			}
		});

		btnBasket_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketBucket bucket = shop.basket.append(selected_item, 1);
				if (!basket_model.contains(bucket)) {
					basket_model.addElement(bucket);
				}
				list.updateUI();
			}
		});

		btnBasket_buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(shop.basket.price());
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				User user = shop.users.users().get(comboBox.getSelectedIndex());
				if (user instanceof Admin) {
					Admin admin = (Admin) user;
				}
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setSelectedIndex(1);
			}
		});

		tabbedPane.setEnabledAt(1, false);
		frmComputerShop.setBounds(100, 100, 820, 548);
		frmComputerShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
