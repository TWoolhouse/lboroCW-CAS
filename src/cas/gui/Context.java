package cas.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import cas.pay.CreditCard;
import cas.pay.PayPal;
import cas.pay.PaymentMethod;
import cas.user.Admin;
import cas.user.User;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLayeredPane;

public class Context {

	private Shop shop;
	private JFrame frmComputerShop;
	private JTabbedPane tabbedPane;
	private JTable table;
	private JTextField txt_payc_num;
	private JTextField txt_payc_sec;

	private Item selected_item = null;
	private User active_user = null;
	private PaymentMethod payment_method;
	private JTextField txt_payp_email;
	private JTable table_1;
	private JTextField txt_barcode;
	private JTextField txt_brand;
	private JTextField txt_colour;
	private JSpinner number_quantity;
	private JSpinner number_cost;
	private JSpinner number_price;

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

	private final class ActionListenerPaymentButton implements ActionListener {
		private final JButton btn;
		private final DefaultListModel<BasketBucket> basket_model;

		private ActionListenerPaymentButton(JButton btn, DefaultListModel<BasketBucket> basket_model) {
			this.btn = btn;
			this.basket_model = basket_model;
		}

		public void actionPerformed(ActionEvent e) {
			Container dialog = this.btn.getParent().getParent().getParent().getParent();
			JOptionPane.showMessageDialog(dialog,
					payment_method.display(shop.basket.price(), active_user.getAddress()), "Payment Confirmation",
					JOptionPane.INFORMATION_MESSAGE);
			dialog.setVisible(false);
			basket_model.removeAllElements();
			// TODO: Reload table
			shop.basket.purchase();
		}
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
		panel_shop.add(panel_customer, "PANEL_CUSTOMER");

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
		DefaultTableModel table_customer = new DefaultTableModel();
		table.setModel(table_customer);

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
		sl_panel_basket.putConstraint(SpringLayout.WEST, lblNewLabel, 2, SpringLayout.WEST, panel_basket);
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

		JSplitPane panel_admin = new JSplitPane();
		panel_admin.setResizeWeight(0.5);
		panel_admin.setContinuousLayout(true);
		panel_shop.add(panel_admin, "PANEL_ADMIN");

		JScrollPane scrollPane_1_1 = new JScrollPane();
		scrollPane_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		panel_admin.setLeftComponent(scrollPane_1_1);

		table_1 = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			};
		};
		table.setFont(Fonts.small);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableModel table_admin = new DefaultTableModel();
		table_1.setModel(table_admin);
		scrollPane_1_1.setViewportView(table_1);

		JPanel panel_create = new JPanel();
		panel_admin.setRightComponent(panel_create);
		SpringLayout sl_panel_create = new SpringLayout();
		panel_create.setLayout(sl_panel_create);

		JLabel lblNewLabel_4 = new JLabel("Create a New Stock Item");
		lblNewLabel_4.setFont(Fonts.medium);
		sl_panel_create.putConstraint(SpringLayout.NORTH, lblNewLabel_4, 0, SpringLayout.NORTH, panel_create);
		sl_panel_create.putConstraint(SpringLayout.WEST, lblNewLabel_4, 2, SpringLayout.WEST, panel_create);
		panel_create.add(lblNewLabel_4);

		JComboBox combo_create_type = new JComboBox();
		combo_create_type.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.NORTH, combo_create_type, 10, SpringLayout.SOUTH, lblNewLabel_4);
		sl_panel_create.putConstraint(SpringLayout.WEST, combo_create_type, 10, SpringLayout.WEST, panel_create);
		sl_panel_create.putConstraint(SpringLayout.EAST, combo_create_type, -10, SpringLayout.EAST, panel_create);
		combo_create_type.setModel(new DefaultComboBoxModel(new String[] { "Keyboard", "Mouse" }));
		panel_create.add(combo_create_type);

		int spread = 16;

		txt_barcode = new JTextField();
		sl_panel_create.putConstraint(SpringLayout.NORTH, txt_barcode, spread * 2, SpringLayout.SOUTH,
				combo_create_type);
		txt_barcode.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, txt_barcode, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, txt_barcode, 0, SpringLayout.EAST, combo_create_type);
		panel_create.add(txt_barcode);

		JLabel lbl_create_1 = new JLabel("Barcode");
		lbl_create_1.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_1, 0, SpringLayout.WEST, txt_barcode);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_1, 0, SpringLayout.NORTH, txt_barcode);
		lbl_create_1.setLabelFor(txt_barcode);
		panel_create.add(lbl_create_1);

		txt_brand = new JTextField();
		txt_brand.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.NORTH, txt_brand, spread, SpringLayout.SOUTH, txt_barcode);
		sl_panel_create.putConstraint(SpringLayout.WEST, txt_brand, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, txt_brand, 0, SpringLayout.EAST, combo_create_type);
		txt_brand.setColumns(10);
		panel_create.add(txt_brand);

		JLabel lbl_create_2 = new JLabel("Brand");
		lbl_create_2.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_2, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_2, 0, SpringLayout.NORTH, txt_brand);
		panel_create.add(lbl_create_2);

		txt_colour = new JTextField();
		txt_colour.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.NORTH, txt_colour, spread, SpringLayout.SOUTH, txt_brand);
		sl_panel_create.putConstraint(SpringLayout.WEST, txt_colour, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, txt_colour, 0, SpringLayout.EAST, combo_create_type);
		txt_colour.setColumns(10);
		panel_create.add(txt_colour);

		JLabel lbl_create_3 = new JLabel("Colour");
		lbl_create_3.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_3, 0, SpringLayout.WEST, txt_colour);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_3, 0, SpringLayout.NORTH, txt_colour);
		panel_create.add(lbl_create_3);

		JLabel lbl_create_4 = new JLabel("Connection");
		lbl_create_4.setFont(Fonts.small);
		panel_create.add(lbl_create_4);

		number_quantity = new JSpinner();
		number_quantity.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		number_quantity.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, number_quantity, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, number_quantity, 0, SpringLayout.EAST, combo_create_type);
		panel_create.add(number_quantity);

		JLabel lbl_create_5 = new JLabel("Quantity");
		lbl_create_5.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_5, 0, SpringLayout.WEST, number_quantity);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_5, 0, SpringLayout.NORTH, number_quantity);
		panel_create.add(lbl_create_5);

		number_cost = new JSpinner();
		number_cost.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		number_cost.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.NORTH, number_cost, spread, SpringLayout.SOUTH, number_quantity);
		sl_panel_create.putConstraint(SpringLayout.WEST, number_cost, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, number_cost, 0, SpringLayout.EAST, combo_create_type);
		panel_create.add(number_cost);

		JLabel lbl_create_6 = new JLabel("Cost");
		lbl_create_6.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_6, 0, SpringLayout.WEST, number_cost);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_6, 0, SpringLayout.NORTH, number_cost);
		panel_create.add(lbl_create_6);

		number_price = new JSpinner();
		number_price.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		number_price.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.NORTH, number_price, spread, SpringLayout.SOUTH, number_cost);
		sl_panel_create.putConstraint(SpringLayout.WEST, number_price, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, number_price, 0, SpringLayout.EAST, combo_create_type);
		panel_create.add(number_price);

		JLabel lbl_create_7 = new JLabel("Price");
		lbl_create_7.setFont(Fonts.small);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_7, 0, SpringLayout.WEST, number_price);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_7, 0, SpringLayout.NORTH, number_price);
		panel_create.add(lbl_create_7);

		JPanel cards_create_type = new JPanel();
		sl_panel_create.putConstraint(SpringLayout.NORTH, cards_create_type, 0, SpringLayout.SOUTH, number_price);
		sl_panel_create.putConstraint(SpringLayout.WEST, cards_create_type, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, cards_create_type, 0, SpringLayout.EAST, combo_create_type);
		panel_create.add(cards_create_type);
		cards_create_type.setLayout(new CardLayout(0, 0));

		JPanel create_keyboard = new JPanel();
		cards_create_type.add(create_keyboard, "CREATE_KEYBOARD");
		SpringLayout sl_create_keyboard = new SpringLayout();
		create_keyboard.setLayout(sl_create_keyboard);

		JComboBox combo_keyboard_layout = new JComboBox();
		combo_keyboard_layout.setModel(new DefaultComboBoxModel(new String[] { "UK", "US" }));
		combo_keyboard_layout.setFont(Fonts.small);
		sl_create_keyboard.putConstraint(SpringLayout.NORTH, combo_keyboard_layout, spread, SpringLayout.NORTH,
				create_keyboard);
		sl_create_keyboard.putConstraint(SpringLayout.WEST, combo_keyboard_layout, 0, SpringLayout.WEST,
				create_keyboard);
		sl_create_keyboard.putConstraint(SpringLayout.EAST, combo_keyboard_layout, 0, SpringLayout.EAST,
				create_keyboard);
		create_keyboard.add(combo_keyboard_layout);

		JLabel lbl_create_keyboard_1 = new JLabel("Keyboard Layout");
		lbl_create_keyboard_1.setFont(Fonts.small);
		sl_create_keyboard.putConstraint(SpringLayout.WEST, lbl_create_keyboard_1, 0, SpringLayout.WEST,
				combo_keyboard_layout);
		sl_create_keyboard.putConstraint(SpringLayout.SOUTH, lbl_create_keyboard_1, 0, SpringLayout.NORTH,
				combo_keyboard_layout);
		create_keyboard.add(lbl_create_keyboard_1);

		JComboBox combo_keyboard_type = new JComboBox();
		combo_keyboard_type.setModel(new DefaultComboBoxModel(new String[] { "Standard", "Flexible", "Gaming" }));
		sl_create_keyboard.putConstraint(SpringLayout.NORTH, combo_keyboard_type, 16, SpringLayout.SOUTH,
				combo_keyboard_layout);
		sl_create_keyboard.putConstraint(SpringLayout.WEST, combo_keyboard_type, 0, SpringLayout.WEST, create_keyboard);
		sl_create_keyboard.putConstraint(SpringLayout.EAST, combo_keyboard_type, 0, SpringLayout.EAST, create_keyboard);
		combo_keyboard_type.setFont(Fonts.small);
		create_keyboard.add(combo_keyboard_type);

		JLabel lbl_create_keyboard_2 = new JLabel("Keyboard Type");
		sl_create_keyboard.putConstraint(SpringLayout.WEST, lbl_create_keyboard_2, 0, SpringLayout.WEST,
				combo_keyboard_layout);
		sl_create_keyboard.putConstraint(SpringLayout.SOUTH, lbl_create_keyboard_2, 0, SpringLayout.NORTH,
				combo_keyboard_type);
		lbl_create_keyboard_2.setFont(Fonts.small);
		create_keyboard.add(lbl_create_keyboard_2);

		JPanel create_mouse = new JPanel();
		cards_create_type.add(create_mouse, "CREATE_MOUSE");
		SpringLayout sl_create_mouse = new SpringLayout();
		create_mouse.setLayout(sl_create_mouse);

		JComboBox combo_mouse_type = new JComboBox();
		sl_create_mouse.putConstraint(SpringLayout.NORTH, combo_mouse_type, 16, SpringLayout.NORTH, create_mouse);
		sl_create_mouse.putConstraint(SpringLayout.WEST, combo_mouse_type, 0, SpringLayout.WEST, create_mouse);
		sl_create_mouse.putConstraint(SpringLayout.EAST, combo_mouse_type, 0, SpringLayout.EAST, create_mouse);
		combo_mouse_type.setFont(Fonts.small);
		create_mouse.add(combo_mouse_type);

		JLabel lbl_create_mouse_1 = new JLabel("Mouse Type");
		sl_create_mouse.putConstraint(SpringLayout.WEST, lbl_create_mouse_1, 0, SpringLayout.WEST, combo_mouse_type);
		sl_create_mouse.putConstraint(SpringLayout.SOUTH, lbl_create_mouse_1, 0, SpringLayout.NORTH, combo_mouse_type);
		lbl_create_mouse_1.setFont(Fonts.small);
		create_mouse.add(lbl_create_mouse_1);

		JSpinner number_mouse_buttons = new JSpinner();
		number_mouse_buttons.setFont(Fonts.small);
		sl_create_mouse.putConstraint(SpringLayout.NORTH, number_mouse_buttons, spread, SpringLayout.SOUTH,
				combo_mouse_type);
		sl_create_mouse.putConstraint(SpringLayout.WEST, number_mouse_buttons, 0, SpringLayout.WEST, create_mouse);
		sl_create_mouse.putConstraint(SpringLayout.EAST, number_mouse_buttons, 0, SpringLayout.EAST, create_mouse);
		number_mouse_buttons.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		create_mouse.add(number_mouse_buttons);

		JLabel lbl_create_mouse_2 = new JLabel("Mouse Buttons");
		sl_create_mouse.putConstraint(SpringLayout.WEST, lbl_create_mouse_2, 0, SpringLayout.WEST,
				number_mouse_buttons);
		sl_create_mouse.putConstraint(SpringLayout.SOUTH, lbl_create_mouse_2, 0, SpringLayout.NORTH,
				number_mouse_buttons);
		lbl_create_mouse_2.setFont(Fonts.small);
		create_mouse.add(lbl_create_mouse_2);

		combo_create_type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout layout = (CardLayout) cards_create_type.getLayout();
				layout.show(cards_create_type, "CREATE_" + combo_create_type.getSelectedItem().toString().toUpperCase());
			}
		});

		JButton create_item = new JButton("Create Stock Item");
		create_item.setFont(Fonts.medium);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, cards_create_type, 0, SpringLayout.NORTH, create_item);
		sl_panel_create.putConstraint(SpringLayout.WEST, create_item, 0, SpringLayout.WEST, cards_create_type);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, create_item, -10, SpringLayout.SOUTH, panel_create);
		sl_panel_create.putConstraint(SpringLayout.EAST, create_item, 0, SpringLayout.EAST, cards_create_type);
		panel_create.add(create_item);

		JComboBox combo_connection = new JComboBox();
		combo_connection.setFont(Fonts.small);
		combo_connection.setModel(new DefaultComboBoxModel(new String[] { "wired", "wireless" }));
		sl_panel_create.putConstraint(SpringLayout.NORTH, number_quantity, spread, SpringLayout.SOUTH,
				combo_connection);
		sl_panel_create.putConstraint(SpringLayout.WEST, lbl_create_4, 0, SpringLayout.WEST, combo_connection);
		sl_panel_create.putConstraint(SpringLayout.SOUTH, lbl_create_4, 0, SpringLayout.NORTH, combo_connection);
		sl_panel_create.putConstraint(SpringLayout.NORTH, combo_connection, spread, SpringLayout.SOUTH, txt_colour);
		sl_panel_create.putConstraint(SpringLayout.WEST, combo_connection, 0, SpringLayout.WEST, combo_create_type);
		sl_panel_create.putConstraint(SpringLayout.EAST, combo_connection, 0, SpringLayout.EAST, combo_create_type);
		panel_create.add(combo_connection);

		JPanel panel_payments = new JPanel();
		panel_shop.add(panel_payments, "name_11891378842800");
		panel_payments.setLayout(new CardLayout(0, 0));

		JPanel panel_pay_creditcard = new JPanel();
		SpringLayout sl_panel_pay_creditcard = new SpringLayout();
		panel_pay_creditcard.setLayout(sl_panel_pay_creditcard);

		JButton btn_payc = new JButton("Pay");
		sl_panel_pay_creditcard.putConstraint(SpringLayout.NORTH, btn_payc, -150, SpringLayout.SOUTH,
				panel_pay_creditcard);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.SOUTH, btn_payc, -100, SpringLayout.SOUTH,
				panel_pay_creditcard);
		txt_payc_num = new JTextField();
		txt_payc_num.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String txt = txt_payc_num.getText().strip();
				if (txt.length() > 6)
					txt = txt.substring(0, 6);
				txt_payc_num.setText(txt);
				((CreditCard) payment_method).setNumber(txt);
				btn_payc.setEnabled(payment_method.valid());
			}
		});
		txt_payc_num.setFont(Fonts.medium);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.NORTH, txt_payc_num, 50, SpringLayout.NORTH,
				panel_pay_creditcard);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.WEST, txt_payc_num, 100, SpringLayout.WEST,
				panel_pay_creditcard);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.SOUTH, txt_payc_num, 100, SpringLayout.NORTH,
				panel_pay_creditcard);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.EAST, txt_payc_num, -100, SpringLayout.EAST,
				panel_pay_creditcard);
		panel_pay_creditcard.add(txt_payc_num);
		txt_payc_num.setColumns(10);

		txt_payc_sec = new JTextField();
		txt_payc_sec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String txt = txt_payc_sec.getText().strip();
				if (txt.length() > 3)
					txt = txt.substring(0, 3);
				txt_payc_sec.setText(txt);
				((CreditCard) payment_method).setSecurity(txt);
				btn_payc.setEnabled(payment_method.valid());
			}
		});
		sl_panel_pay_creditcard.putConstraint(SpringLayout.NORTH, txt_payc_sec, 16, SpringLayout.SOUTH, txt_payc_num);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.SOUTH, txt_payc_sec, 66, SpringLayout.SOUTH, txt_payc_num);
		txt_payc_sec.setFont(Fonts.medium);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.WEST, txt_payc_sec, 0, SpringLayout.WEST, txt_payc_num);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.EAST, txt_payc_sec, 0, SpringLayout.EAST, txt_payc_num);
		txt_payc_sec.setColumns(10);
		panel_pay_creditcard.add(txt_payc_sec);

		btn_payc.addActionListener(new ActionListenerPaymentButton(btn_payc, basket_model));
		btn_payc.setEnabled(false);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.WEST, btn_payc, 0, SpringLayout.WEST, txt_payc_sec);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.EAST, btn_payc, 0, SpringLayout.EAST, txt_payc_sec);
		panel_pay_creditcard.add(btn_payc);

		JLabel lblNewLabel_1 = new JLabel("6-Digit Card Number");
		lblNewLabel_1.setFont(Fonts.small);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.WEST, lblNewLabel_1, 0, SpringLayout.WEST, txt_payc_num);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.SOUTH, lblNewLabel_1, 0, SpringLayout.NORTH, txt_payc_num);
		panel_pay_creditcard.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Security Code");
		lblNewLabel_2.setFont(Fonts.small);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.WEST, lblNewLabel_2, 0, SpringLayout.WEST, txt_payc_sec);
		sl_panel_pay_creditcard.putConstraint(SpringLayout.SOUTH, lblNewLabel_2, 0, SpringLayout.NORTH, txt_payc_sec);
		panel_pay_creditcard.add(lblNewLabel_2);

		JPanel panel_pay_paypal = new JPanel();
		SpringLayout sl_panel_pay_paypal = new SpringLayout();
		panel_pay_paypal.setLayout(sl_panel_pay_paypal);

		JButton btn_payp = new JButton("Pay");
		btn_payp.setEnabled(false);
		btn_payp.setFont(Fonts.medium);
		sl_panel_pay_paypal.putConstraint(SpringLayout.NORTH, btn_payp, -150, SpringLayout.SOUTH, panel_pay_paypal);
		sl_panel_pay_paypal.putConstraint(SpringLayout.SOUTH, btn_payp, -100, SpringLayout.SOUTH, panel_pay_paypal);
		panel_pay_paypal.add(btn_payp);

		txt_payp_email = new JTextField();
		txt_payp_email.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String txt = txt_payp_email.getText().strip();
				txt_payp_email.setText(txt);
				((PayPal) payment_method).setEmail(txt);
				btn_payp.setEnabled(payment_method.valid());
			}
		});
		txt_payp_email.setFont(Fonts.medium);
		sl_panel_pay_paypal.putConstraint(SpringLayout.WEST, btn_payp, 0, SpringLayout.WEST, txt_payp_email);
		sl_panel_pay_paypal.putConstraint(SpringLayout.EAST, btn_payp, 0, SpringLayout.EAST, txt_payp_email);
		sl_panel_pay_paypal.putConstraint(SpringLayout.NORTH, txt_payp_email, 100, SpringLayout.NORTH,
				panel_pay_paypal);
		sl_panel_pay_paypal.putConstraint(SpringLayout.WEST, txt_payp_email, 100, SpringLayout.WEST, panel_pay_paypal);
		sl_panel_pay_paypal.putConstraint(SpringLayout.SOUTH, txt_payp_email, 150, SpringLayout.NORTH,
				panel_pay_paypal);
		sl_panel_pay_paypal.putConstraint(SpringLayout.EAST, txt_payp_email, -100, SpringLayout.EAST, panel_pay_paypal);
		panel_pay_paypal.add(txt_payp_email);

		btn_payp.addActionListener(new ActionListenerPaymentButton(btn_payp, basket_model));

		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setFont(Fonts.small);
		sl_panel_pay_paypal.putConstraint(SpringLayout.WEST, lblNewLabel_3, 0, SpringLayout.WEST, txt_payp_email);
		sl_panel_pay_paypal.putConstraint(SpringLayout.SOUTH, lblNewLabel_3, 0, SpringLayout.NORTH, txt_payp_email);
		panel_pay_paypal.add(lblNewLabel_3);

		btnBasket_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				basket_model.removeAllElements();
				shop.basket.empty();
			}
		});

		btnBasket_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BasketBucket bucket = shop.basket.append(selected_item, 1);
				if (bucket.getCount() > bucket.getItem().getQuantity()) {
					JOptionPane.showMessageDialog(frmComputerShop,
							"Sorry, there is no more stock in the shop.", "Too Many Items", JOptionPane.ERROR_MESSAGE);
					bucket.setCount(bucket.getItem().getQuantity());
				}
				if (!basket_model.contains(bucket)) {
					basket_model.addElement(bucket);
				}
				list.updateUI();
			}
		});

		btnBasket_buy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(shop.basket.price());
				String[] options = new String[] {
						"Credit Card",
						"PayPal",
						"Cancel",
				};
				int response = JOptionPane.showOptionDialog(frmComputerShop, "How would you like to pay?",
						"Payment Method",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

				switch (response) {
					case 0: // Credit Card
						payment_method = new CreditCard();
						txt_payc_num.setText("");
						txt_payc_sec.setText("");
						payment_dialog(panel_pay_creditcard, "Credit Card Payment");
						break;
					case 1: // PayPal
						payment_method = new PayPal();
						txt_payc_sec.setText("");
						payment_dialog(panel_pay_paypal, "PayPal Payment");
						break;
					default: // Cancel
						return;
				}
			}

			private void payment_dialog(JPanel panel, String msg) {
				JDialog dialog = new JDialog(frmComputerShop, msg, true);
				dialog.setContentPane(panel);
				dialog.setSize(400, 400);
				Rectangle fb = dialog.getParent().getBounds();
				dialog.setLocation(fb.x + fb.width / 2 - 200, fb.y + fb.height / 2 - 200);
				dialog.setVisible(true);
			}
		});

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active_user = shop.users.users().get(comboBox.getSelectedIndex());
				CardLayout layout = (CardLayout) panel_shop.getLayout();
				if (active_user instanceof Admin) {
					Admin admin = (Admin) active_user;
					table_admin.setDataVector(table_data(), new String[] {
							"Type", "Barcode", "Brand", "Colour", "Connectivity", "Type", "Language/Buttons", "Quantity", "Price", "Original"
					});
					layout.show(panel_shop, "PANEL_ADMIN");
				} else {
					table_customer.setDataVector(table_data(), new String[] {
							"Type", "Barcode", "Brand", "Colour", "Connectivity", "Type", "Language/Buttons", "Quantity", "Price"
					});
					shop.basket.empty();
					basket_model.clear();
					layout.show(panel_shop, "PANEL_CUSTOMER");
				}
				tabbedPane.setEnabledAt(1, true);
				tabbedPane.setSelectedIndex(1);
			}
		});

		tabbedPane.setEnabledAt(1, false);
		frmComputerShop.setBounds(100, 100, 820, 548);
		frmComputerShop.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Debugging
		// panel_payments.add(panel_pay_creditcard);
		// panel_payments.add(panel_pay_paypal);

	}

	private Object[][] table_data() {
		ArrayList<Object[]> rows = new ArrayList<Object[]>(shop.inventory.items.size());
		for (Item item : shop.inventory.items.values()) {
			rows.add(item.display().toArray());
		}
		rows.sort((arg0, arg1) -> Double.compare(Double.parseDouble((String) arg0[8]),
				Double.parseDouble((String) arg1[8])));
		return (Object[][]) rows
				.toArray(new Object[rows.get(0).length][rows.size()]);
	}
}
