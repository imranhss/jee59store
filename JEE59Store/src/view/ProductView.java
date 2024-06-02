/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.DbUtil;

/**
 *
 * @author user
 */
public class ProductView extends javax.swing.JFrame {

    DbUtil db = new DbUtil();
    PreparedStatement ps;
    ResultSet rs;
    public  static float stockQuantity=0;

    LocalDate currentDate = LocalDate.now();
    java.sql.Date sqlCurrentDate = java.sql.Date.valueOf(currentDate);

    /**
     * Creates new form ProductView
     */
    public ProductView() {
        initComponents();
        showProductOnTable();
        showProductToCombo();
        showStockOnTable();

        comProductName.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                getProductSalesPrice(e);
            }

        });

    }

    public void getSalesReport() {

        String[] salesViewTableColumn = {"SL", "Name", "Unit Price", "Qunatity", "Total Price", "Date"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(salesViewTableColumn);
        tblReports.setModel(model);

        Date toDate = dateToReport.getDate();
        Date fromDate = dateFromReport.getDate();

        String sql = "select * from sales where salesDate between ? and ?";

        String sql1 = "select sum(salesTotalPrice) from sales where salesDate between ? and ?";

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));

            rs = ps.executeQuery();

            int sl = 1;

            while (rs.next()) {

                //int id = rs.getInt("id");
                String name = rs.getString("name");
                float unitPrice = rs.getFloat("salesUnitPrice");
                float quantity = rs.getFloat("salesQuantity");
                float totalPrice = rs.getFloat("salesTotalPrice");

                Date salesDate = rs.getDate("salesDate");

                model.addRow(new Object[]{sl, name, unitPrice, quantity, totalPrice, salesDate});

                sl += 1;
            }

            ps.close();
            rs.close();
            db.getCon().close();

            ps = db.getCon().prepareStatement(sql1);

            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));

            rs = ps.executeQuery();

            while (rs.next()) {
                float totalPrice = rs.getFloat("sum(salesTotalPrice)");
                System.out.println(totalPrice);
                model.addRow(new Object[]{"", "", "", "Total Amount", totalPrice});
            }

            ps.close();
            rs.close();
            db.getCon().close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getGrossProfit() {
        
        Date toDate = dateToReport.getDate();
        Date fromDate = dateFromReport.getDate();

        String sql1 = "select sum(salesTotalPrice) from sales where salesDate between ? and ?";

        String sql = "select sum(totalPrice) from product where purcahseDate between ? and ?";

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));

            rs = ps.executeQuery();

            float totalPurchasePrice = 0;

            while (rs.next()) {

                //int id = rs.getInt("id");
                totalPurchasePrice = rs.getFloat("sum(totalPrice)");

            }

            ps.close();
            rs.close();
            db.getCon().close();

            
            // For Sales 
            
            ps = db.getCon().prepareStatement(sql1);

            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));

            rs = ps.executeQuery();
            float totalSalesPrice=0;
            
            while (rs.next()) {
                
                 totalSalesPrice = rs.getFloat("sum(salesTotalPrice)");
               
            }

            ps.close();
            rs.close();
            db.getCon().close();
            
            float grossProfit= totalSalesPrice-totalPurchasePrice;
            
            lblProfit.setText("Profit: "+grossProfit);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getPurchaseReport() {

        String[] productViewTableColumn = {"SL", "Name", "Unit Price", "Qunatity", "Total Price", "Sales Price", "Date"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(productViewTableColumn);
        tblReports.setModel(model);

        Date toDate = dateToReport.getDate();
        Date fromDate = dateFromReport.getDate();

        String sql = "select * from product where purcahseDate between ? and ?";
        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));

            rs = ps.executeQuery();

            int sl = 1;

            while (rs.next()) {

                //int id = rs.getInt("id");
                String name = rs.getString("name");
                float unitPrice = rs.getFloat("unitPrice");
                float quantity = rs.getFloat("quantity");
                float totalPrice = rs.getFloat("totalPrice");
                float salesPrice = rs.getFloat("salesPrice");
                Date purcahseDate = rs.getDate("purcahseDate");

                model.addRow(new Object[]{sl, name, unitPrice, quantity, totalPrice, salesPrice, purcahseDate});

                sl += 1;
            }

            ps.close();
            rs.close();
            db.getCon().close();

            String sql1 = "select sum(totalPrice) from product where purcahseDate between ? and ?";

            ps = db.getCon().prepareStatement(sql1);

            ps.setDate(1, convertUtilDateToSqlDate(fromDate));
            ps.setDate(2, convertUtilDateToSqlDate(toDate));

            rs = ps.executeQuery();

            while (rs.next()) {

                //int id = rs.getInt("id");
                float totalPrice = rs.getFloat("sum(totalPrice)");

                model.addRow(new Object[]{"", "", "", "Total Amount", totalPrice});

            }

            ps.close();
            rs.close();
            db.getCon().close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean getStocProductList() {
        String sql = "select name from stock";
        boolean status = false;
        String purchaseProductName = txtName.getText().trim();

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("name");
                if (productName.equalsIgnoreCase(purchaseProductName)) {
                    status = true;
                    break;
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public void addProdductToStock() {

        boolean status = getStocProductList();

        if (status) {
            String sql = "update stock set quantity=quantity+? where name=?";
            try {
                ps = db.getCon().prepareStatement(sql);

                ps.setFloat(1, Float.parseFloat(txtQuantity.getText().trim()));
                ps.setString(2, txtName.getText().trim());

                ps.executeUpdate();

                ps.close();
                db.getCon().close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            String sql = "insert into stock(name,purcahsePrice,quantity) values (?,?,?)";
            PreparedStatement ps;

            try {
                ps = db.getCon().prepareStatement(sql);

                ps.setString(1, txtName.getText().trim());
                ps.setFloat(2, Float.parseFloat(txtUnitPrice.getText().trim()));
                ps.setFloat(3, Float.parseFloat(txtQuantity.getText().trim()));

                ps.executeUpdate();

                ps.close();
                db.getCon().close();

            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void addProduct() {

        String sql = "insert into product(name,unitPrice,quantity,totalPrice, salesPrice,purcahseDate) values (?,?,?,?,?,?)";
        PreparedStatement ps;

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setString(1, txtName.getText().trim());
            ps.setFloat(2, Float.parseFloat(txtUnitPrice.getText().trim()));
            ps.setFloat(3, Float.parseFloat(txtQuantity.getText().trim()));
            ps.setFloat(4, Float.parseFloat(txtTotalPrice.getText().trim()));
            ps.setFloat(5, Float.parseFloat(txtSalesPrice.getText().trim()));
            ps.setDate(6, sqlCurrentDate);

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(this, "Add Product Successfully");
            addProdductToStock();
            showStockOnTable();
            clear();
            showProductOnTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Add Product unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Add Product unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getTotalPrice() {

        float unitPrice = Float.parseFloat(txtUnitPrice.getText().trim());
        float quantity = Float.parseFloat(txtQuantity.getText().trim());

        float totalPrice = unitPrice * quantity;

        txtTotalPrice.setText(totalPrice + "");

    }

    public void clear() {
        btnProductAdd.setVisible(true);
        txtId.setText("");
        txtName.setText("");
        txtUnitPrice.setText("");
        txtQuantity.setText("");
        txtTotalPrice.setText("");
        txtSalesPrice.setText("");

    }

    String[] productViewTableColumn = {"id", "Name", "Unit Price", "Qunatity", "Total Price", "Sales Price"};
    String[] stockViewTableColumn = {"id", "Name", "Qunatity", "Unit Price"};

    public void showProductOnTable() {

        String sql = "select * from product";
        PreparedStatement ps;
        ResultSet rs;

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(productViewTableColumn);

        tblProductView.setModel(model);

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                float unitPrice = rs.getFloat("unitPrice");
                float quantity = rs.getFloat("quantity");
                float totalPrice = rs.getFloat("totalPrice");
                float salesPrice = rs.getFloat("salesPrice");

                model.addRow(new Object[]{id, name, unitPrice, quantity, totalPrice, salesPrice});

            }

            rs.close();
            ps.close();
            db.getCon();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showStockOnTable() {

        String sql = "select * from stock";
        PreparedStatement ps;
        ResultSet rs;

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(stockViewTableColumn);

        tblStock.setModel(model);

        try {
            ps = db.getCon().prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String name = rs.getString("name");
                float quantity = rs.getFloat("quantity");
                float unitPrice = rs.getFloat("purcahsePrice");

                model.addRow(new Object[]{id, name, unitPrice, quantity});

            }

            rs.close();
            ps.close();
            db.getCon();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteProduct() {
        String sql = "delete from product where id=?";
        PreparedStatement ps;

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(txtId.getText()));
            ps.executeUpdate();

            ps.close();
            db.getCon();
            JOptionPane.showMessageDialog(this, "Delete Product Successfully");
            clear();
            showProductOnTable();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Delete Product Unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete Product Unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void editProduct() {
        String sql = "update product set name=?, unitPrice=?, quantity=?, totalPrice=?, salesPrice=? where id=?";
        PreparedStatement ps;

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setString(1, txtName.getText().trim());
            ps.setFloat(2, Float.parseFloat(txtUnitPrice.getText().trim()));
            ps.setFloat(3, Float.parseFloat(txtQuantity.getText().trim()));
            ps.setFloat(4, Float.parseFloat(txtTotalPrice.getText().trim()));
            ps.setFloat(5, Float.parseFloat(txtSalesPrice.getText().trim()));
            ps.setInt(6, Integer.parseInt(txtId.getText()));

            ps.executeUpdate();

            ps.close();
            db.getCon();
            JOptionPane.showMessageDialog(this, "Update Product Successfully");
            clear();
            showProductOnTable();

        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Update Product Unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update Product Unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void showProductToCombo() {
        String sql = "select name from product";
        PreparedStatement ps;
        ResultSet rs;

        comProductName.removeAllItems();

        try {
            ps = db.getCon().prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {
                String productName = rs.getString("name");
                comProductName.addItem(productName);
            }

            ps.close();
            db.getCon().close();
            rs.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getProductSalesPrice(ItemEvent e) {

        String selectedProductName = "";

        if (e.getStateChange() == ItemEvent.SELECTED) {
            selectedProductName = (String) e.getItem();
            //TODO your actitons
            extractSalesPrice(selectedProductName);
        }

        String sql = "Select quantity from stock where name=?";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, selectedProductName);

            rs = ps.executeQuery();

            while (rs.next()) {
                 stockQuantity = rs.getFloat("quantity");
                lblStock.setText(stockQuantity + "");

            }

            ps.close();
            db.getCon().close();
            rs.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    public void salesValidationforQuantity(){
        float salesQuantity=  Float.parseFloat(txtSalesQunatity.getText().trim());
        
        if(salesQuantity>stockQuantity){
            JOptionPane.showMessageDialog(this, "Sales quantity is more then Stock");
            txtSalesQunatity.setText("0");
            txtSalesQunatity.requestFocus();
        }
        else{
        
        }
    
    
    }
    

    public void extractSalesPrice(String productName) {

        String sql = "select salesPrice from product where name=?";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, productName);

            rs = ps.executeQuery();

            while (rs.next()) {
                String salesPrice = rs.getString("salesPrice");
                txtSalesUnitPrice.setText(salesPrice);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void getTotalSalesPrice() {

        float quantity = Float.parseFloat(txtSalesQunatity.getText().toString().trim());
        float unitPrice = Float.parseFloat(txtSalesUnitPrice.getText().toString().trim());
        float salesTotalPrice = quantity * unitPrice;
        txtSalesTotalPrice.setText(salesTotalPrice + "");

    }

    public String formatDateToDDMMYYYY(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }

    public static java.sql.Date convertUtilDateToSqlDate(java.util.Date utilDate) {
        if (utilDate != null) {
            return new java.sql.Date(utilDate.getTime());
        }
        return null;
    }

    public static java.sql.Date convertStringToSqlDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            java.util.Date utilDate = inputFormat.parse(dateString);

            // Convert to "yyyy-MM-dd" format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = outputFormat.format(utilDate);

            return java.sql.Date.valueOf(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null or handle the error as needed
        }
    }

    public void stockUpdateOnSales() {
        String sql = "update stock set quantity=quantity-? where name=?";

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setFloat(1, Float.parseFloat(txtSalesQunatity.getText().trim()));
            ps.setString(2, comProductName.getSelectedItem().toString());

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addSales() {

        Date date = convertUtilDateToSqlDate(salesDate.getDate());

        PreparedStatement ps;
        String sql = "insert into sales(name, salesUnitPrice,salesQuantity,salesTotalPrice,salesDate) "
                + "values(?,?,?,?,?)";

        try {
            ps = db.getCon().prepareStatement(sql);
            ps.setString(1, comProductName.getSelectedItem().toString());
            ps.setFloat(2, Float.parseFloat(txtSalesUnitPrice.getText()));
            ps.setFloat(3, Float.parseFloat(txtSalesQunatity.getText()));
            ps.setFloat(4, Float.parseFloat(txtSalesTotalPrice.getText()));
            ps.setDate(5, convertUtilDateToSqlDate(date));

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(this, "Add Sales Successfully");
            stockUpdateOnSales();
            showStockOnTable();

        } catch (ClassNotFoundException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Add Sales Unsuccessfully");
            Logger.getLogger(ProductView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnAddProduct = new javax.swing.JButton();
        btnSalesProduct = new javax.swing.JButton();
        btnStock = new javax.swing.JButton();
        btnReports = new javax.swing.JButton();
        mainView = new javax.swing.JTabbedPane();
        add = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUnitPrice = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTotalPrice = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSalesPrice = new javax.swing.JTextField();
        btnProductAdd = new javax.swing.JButton();
        btnProductDelete = new javax.swing.JButton();
        btnProductEdit = new javax.swing.JButton();
        btnProductReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductView = new javax.swing.JTable();
        sales = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        comProductName = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        txtSalesQunatity = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        salesDate = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtSalesUnitPrice = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSalesTotalPrice = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnSalesSave = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        stock = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStock = new javax.swing.JTable();
        report = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        dateFromReport = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jLabel20 = new javax.swing.JLabel();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        dateToReport = new com.toedter.calendar.JDateChooser();
        btnReportPurchase = new javax.swing.JButton();
        btnReportSales = new javax.swing.JButton();
        btnReportGrossProfit = new javax.swing.JButton();
        btnReportReset = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblReports = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        lblProfit = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 650));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("JEE 59 Shop");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 100));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, -1));

        jPanel1.setBackground(new java.awt.Color(0, 9, 32));

        btnAddProduct.setText("Add Product");
        btnAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddProductMouseClicked(evt);
            }
        });

        btnSalesProduct.setText("Sales Product");
        btnSalesProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalesProductMouseClicked(evt);
            }
        });

        btnStock.setText("Stock ");
        btnStock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnStockMouseClicked(evt);
            }
        });

        btnReports.setText("Report");
        btnReports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportsMouseClicked(evt);
            }
        });
        btnReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSalesProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(btnStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnAddProduct)
                .addGap(18, 18, 18)
                .addComponent(btnSalesProduct)
                .addGap(18, 18, 18)
                .addComponent(btnStock)
                .addGap(18, 18, 18)
                .addComponent(btnReports)
                .addContainerGap(329, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 200, 500));

        jPanel4.setBackground(new java.awt.Color(0, 102, 102));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Add Product");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 60));

        jLabel6.setText("ID");

        txtId.setEditable(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        jLabel7.setText("Name");

        jLabel8.setText("Unit Price");

        jLabel9.setText("Quantity");

        txtQuantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtQuantityFocusLost(evt);
            }
        });

        jLabel10.setText("Total Price");

        txtTotalPrice.setEditable(false);
        txtTotalPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalPriceFocusLost(evt);
            }
        });
        txtTotalPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalPriceActionPerformed(evt);
            }
        });

        jLabel11.setText("Sales Price");

        btnProductAdd.setText("Add");
        btnProductAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductAddMouseClicked(evt);
            }
        });

        btnProductDelete.setText("Delete");
        btnProductDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductDeleteMouseClicked(evt);
            }
        });

        btnProductEdit.setText("Edit");
        btnProductEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductEditMouseClicked(evt);
            }
        });

        btnProductReset.setText("Reset");
        btnProductReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnProductResetMouseClicked(evt);
            }
        });

        tblProductView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductView);

        javax.swing.GroupLayout addLayout = new javax.swing.GroupLayout(add);
        add.setLayout(addLayout);
        addLayout.setHorizontalGroup(
            addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(addLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(addLayout.createSequentialGroup()
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtId))
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, addLayout.createSequentialGroup()
                                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(addLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtSalesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(73, 73, 73)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(addLayout.createSequentialGroup()
                        .addComponent(btnProductAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProductDelete))
                    .addGroup(addLayout.createSequentialGroup()
                        .addComponent(btnProductEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProductReset)))
                .addGap(153, 153, 153))
            .addComponent(jScrollPane1)
        );
        addLayout.setVerticalGroup(
            addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(addLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProductAdd)
                    .addComponent(btnProductDelete))
                .addGap(20, 20, 20)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnProductEdit)
                        .addComponent(btnProductReset)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(addLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSalesPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        mainView.addTab("Add", add);

        jPanel5.setBackground(new java.awt.Color(204, 0, 102));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Product Sales");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 50));

        jLabel3.setText("Name");

        comProductName.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comProductName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comProductNameActionPerformed(evt);
            }
        });

        jLabel13.setText("Quantity");

        txtSalesQunatity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSalesQunatityFocusLost(evt);
            }
        });

        jLabel15.setText("Date");

        jLabel14.setText("UnitPrice");

        txtSalesUnitPrice.setEditable(false);

        jLabel16.setText("Total Price");

        txtSalesTotalPrice.setEditable(false);

        jButton1.setText("Edit");

        jButton2.setText("Delete");

        btnSalesSave.setText("Save");
        btnSalesSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSalesSaveMouseClicked(evt);
            }
        });

        jButton5.setText("Rest");

        jLabel18.setText("Stock");

        javax.swing.GroupLayout salesLayout = new javax.swing.GroupLayout(sales);
        sales.setLayout(salesLayout);
        salesLayout.setHorizontalGroup(
            salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(salesLayout.createSequentialGroup()
                .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(comProductName, 0, 152, Short.MAX_VALUE)
                            .addComponent(txtSalesUnitPrice)))
                    .addGroup(salesLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnSalesSave)
                        .addGap(52, 52, 52)
                        .addComponent(jButton1)))
                .addGap(61, 61, 61)
                .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(salesLayout.createSequentialGroup()
                        .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel16))
                        .addGap(35, 35, 35)
                        .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSalesQunatity, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                            .addComponent(txtSalesTotalPrice))
                        .addGap(42, 42, 42)
                        .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(salesLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(salesDate, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(salesLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(lblStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(salesLayout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(67, 67, 67)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        salesLayout.setVerticalGroup(
            salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(salesLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comProductName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(txtSalesQunatity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15))
                    .addComponent(salesDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtSalesUnitPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(txtSalesTotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(lblStock, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54)
                .addGroup(salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(btnSalesSave)
                    .addComponent(jButton5))
                .addGap(0, 271, Short.MAX_VALUE))
        );

        mainView.addTab("sales", sales);

        jPanel6.setBackground(new java.awt.Color(255, 51, 102));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Stock ");
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 60));

        tblStock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblStock);

        javax.swing.GroupLayout stockLayout = new javax.swing.GroupLayout(stock);
        stock.setLayout(stockLayout);
        stockLayout.setHorizontalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
        );
        stockLayout.setVerticalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainView.addTab("stock", stock);

        report.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel7.setBackground(new java.awt.Color(255, 102, 153));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Reports");
        jPanel7.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 70));

        report.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 70));
        report.add(dateFromReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 260, -1));

        jLabel5.setText("From");
        report.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 40, 20));

        jLabel19.setText("From");
        report.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 40, 20));
        report.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 170, -1));

        jLabel20.setText("From");
        report.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 40, 20));
        report.add(jDateChooser3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 170, -1));

        jLabel21.setText("From");
        report.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 40, 20));

        jLabel22.setText("To");
        report.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 100, 30, 20));
        report.add(dateToReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 260, -1));

        btnReportPurchase.setText("Purchase");
        btnReportPurchase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportPurchaseMouseClicked(evt);
            }
        });
        report.add(btnReportPurchase, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

        btnReportSales.setText("Sales");
        btnReportSales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportSalesMouseClicked(evt);
            }
        });
        report.add(btnReportSales, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, -1));

        btnReportGrossProfit.setText("Gross Profit");
        btnReportGrossProfit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportGrossProfitMouseClicked(evt);
            }
        });
        report.add(btnReportGrossProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, -1, -1));

        btnReportReset.setText("Reset");
        btnReportReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnReportResetMouseClicked(evt);
            }
        });
        report.add(btnReportReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, -1));

        tblReports.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblReports);

        report.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 800, 280));

        jPanel8.setBackground(new java.awt.Color(255, 102, 153));
        jPanel8.setForeground(new java.awt.Color(255, 102, 153));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProfit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblProfit.setForeground(new java.awt.Color(255, 255, 255));
        lblProfit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProfit.setText("Profit : ");
        jPanel8.add(lblProfit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 80));

        report.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 260, 80));

        mainView.addTab("report", report);

        getContentPane().add(mainView, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 800, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddProductMouseClicked
        // TODO add your handling code here:

        mainView.setSelectedIndex(0);


    }//GEN-LAST:event_btnAddProductMouseClicked

    private void btnSalesProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalesProductMouseClicked
        // TODO add your handling code here:
        mainView.setSelectedIndex(1);

        showProductToCombo();


    }//GEN-LAST:event_btnSalesProductMouseClicked

    private void btnStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseClicked
        // TODO add your handling code here:
        mainView.setSelectedIndex(2);
    }//GEN-LAST:event_btnStockMouseClicked

    private void btnReportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportsMouseClicked
        // TODO add your handling code here:
        mainView.setSelectedIndex(3);
    }//GEN-LAST:event_btnReportsMouseClicked

    private void txtTotalPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalPriceActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    private void btnProductAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductAddMouseClicked
        // TODO add your handling code here:
        addProduct();

    }//GEN-LAST:event_btnProductAddMouseClicked

    private void txtTotalPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalPriceFocusLost
        // TODO add your handling code here:


    }//GEN-LAST:event_txtTotalPriceFocusLost

    private void txtQuantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtQuantityFocusLost
        // TODO add your handling code here:
        getTotalPrice();

    }//GEN-LAST:event_txtQuantityFocusLost

    private void btnProductResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductResetMouseClicked
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnProductResetMouseClicked

    private void tblProductViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductViewMouseClicked
        // TODO add your handling code here:

        int rowIndex = tblProductView.getSelectedRow();

        String id = tblProductView.getModel().getValueAt(rowIndex, 0).toString();
        String name = tblProductView.getModel().getValueAt(rowIndex, 1).toString();
        String unitPrice = tblProductView.getModel().getValueAt(rowIndex, 2).toString();
        String quantity = tblProductView.getModel().getValueAt(rowIndex, 3).toString();
        String totalPrice = tblProductView.getModel().getValueAt(rowIndex, 4).toString();
        String salesPrice = tblProductView.getModel().getValueAt(rowIndex, 5).toString();

        txtId.setText(id);
        txtName.setText(name);
        txtUnitPrice.setText(unitPrice);
        txtQuantity.setText(quantity);
        txtTotalPrice.setText(totalPrice);
        txtSalesPrice.setText(salesPrice);
        
        btnProductAdd.setVisible(false);

    }//GEN-LAST:event_tblProductViewMouseClicked

    private void btnProductDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductDeleteMouseClicked
        // TODO add your handling code here:

        deleteProduct();
    }//GEN-LAST:event_btnProductDeleteMouseClicked

    private void btnProductEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductEditMouseClicked
        // TODO add your handling code here:
        editProduct();

    }//GEN-LAST:event_btnProductEditMouseClicked

    private void comProductNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comProductNameActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_comProductNameActionPerformed

    private void txtSalesQunatityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSalesQunatityFocusLost
        // TODO add your handling code here:
        getTotalSalesPrice();
        salesValidationforQuantity();  
        
        

    }//GEN-LAST:event_txtSalesQunatityFocusLost

    private void btnSalesSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalesSaveMouseClicked
        // TODO add your handling code here:
        addSales();

    }//GEN-LAST:event_btnSalesSaveMouseClicked

    private void btnReportPurchaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportPurchaseMouseClicked
        // TODO add your handling code here:
        getPurchaseReport();
    }//GEN-LAST:event_btnReportPurchaseMouseClicked

    private void btnReportSalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportSalesMouseClicked
        // TODO add your handling code here:
        getSalesReport();
    }//GEN-LAST:event_btnReportSalesMouseClicked

    private void btnReportGrossProfitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportGrossProfitMouseClicked
        // TODO add your handling code here:
        getGrossProfit();
    }//GEN-LAST:event_btnReportGrossProfitMouseClicked

    private void btnReportResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnReportResetMouseClicked
        // TODO add your handling code here:
        
        DefaultTableModel model=new DefaultTableModel();
        tblReports.setModel(model);
        model.setRowCount(0);
        
    }//GEN-LAST:event_btnReportResetMouseClicked

    private void btnReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportsActionPerformed
        // TODO add your handling code here:
        DefaultTableModel model=new DefaultTableModel();
        tblReports.setModel(model);
        model.setRowCount(0);
    }//GEN-LAST:event_btnReportsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel add;
    private javax.swing.JButton btnAddProduct;
    private javax.swing.JButton btnProductAdd;
    private javax.swing.JButton btnProductDelete;
    private javax.swing.JButton btnProductEdit;
    private javax.swing.JButton btnProductReset;
    private javax.swing.JButton btnReportGrossProfit;
    private javax.swing.JButton btnReportPurchase;
    private javax.swing.JButton btnReportReset;
    private javax.swing.JButton btnReportSales;
    private javax.swing.JButton btnReports;
    private javax.swing.JButton btnSalesProduct;
    private javax.swing.JButton btnSalesSave;
    private javax.swing.JButton btnStock;
    private javax.swing.JComboBox<String> comProductName;
    private com.toedter.calendar.JDateChooser dateFromReport;
    private com.toedter.calendar.JDateChooser dateToReport;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblProfit;
    private javax.swing.JLabel lblStock;
    private javax.swing.JTabbedPane mainView;
    private javax.swing.JPanel report;
    private javax.swing.JPanel sales;
    private com.toedter.calendar.JDateChooser salesDate;
    private javax.swing.JPanel stock;
    private javax.swing.JTable tblProductView;
    private javax.swing.JTable tblReports;
    private javax.swing.JTable tblStock;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSalesPrice;
    private javax.swing.JTextField txtSalesQunatity;
    private javax.swing.JTextField txtSalesTotalPrice;
    private javax.swing.JTextField txtSalesUnitPrice;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}
