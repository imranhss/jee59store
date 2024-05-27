/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    /**
     * Creates new form ProductView
     */
    public ProductView() {
        initComponents();
        showProductOnTable();
    }

    public void addProduct() {

        String sql = "insert into product(name,unitPrice,quantity,totalPrice, salesPrice) values (?,?,?,?,?)";
        PreparedStatement ps;

        try {
            ps = db.getCon().prepareStatement(sql);

            ps.setString(1, txtName.getText().trim());
            ps.setFloat(2, Float.parseFloat(txtUnitPrice.getText().trim()));
            ps.setFloat(3, Float.parseFloat(txtQuantity.getText().trim()));
            ps.setFloat(4, Float.parseFloat(txtTotalPrice.getText().trim()));
            ps.setFloat(5, Float.parseFloat(txtSalesPrice.getText().trim()));

            ps.executeUpdate();

            ps.close();
            db.getCon().close();

            JOptionPane.showMessageDialog(this, "Add Product Successfully");
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
        txtId.setText("");
        txtName.setText("");
        txtUnitPrice.setText("");
        txtQuantity.setText("");
        txtTotalPrice.setText("");
        txtSalesPrice.setText("");

    }

    String[] productViewTableColumn = {"id", "Name", "Unit Price", "Qunatity", "Total Price", "Sales Price"};

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

    public void deleteProduct() {
        String sql = "delete from product where id=?";
        PreparedStatement ps;
       
        try {
            ps=db.getCon().prepareStatement(sql);
            
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
    
    
    public void editProduct(){
        String sql = "update product set name=?, unitPrice=?, quantity=?, totalPrice=?, salesPrice=? where id=?";
        PreparedStatement ps;
        
        try {
            ps=db.getCon().prepareStatement(sql);
            
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
        jButton4 = new javax.swing.JButton();
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
        jLabel3 = new javax.swing.JLabel();
        stock = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        report = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

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

        jButton4.setText("Report");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnAddProduct, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSalesProduct, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addComponent(btnStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jButton4)
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainView.addTab("Add", add);

        jLabel3.setText("Sales");

        javax.swing.GroupLayout salesLayout = new javax.swing.GroupLayout(sales);
        sales.setLayout(salesLayout);
        salesLayout.setHorizontalGroup(
            salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesLayout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(271, 271, 271))
        );
        salesLayout.setVerticalGroup(
            salesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salesLayout.createSequentialGroup()
                .addContainerGap(207, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(130, 130, 130))
        );

        mainView.addTab("sales", sales);

        jLabel4.setText("Stock");

        javax.swing.GroupLayout stockLayout = new javax.swing.GroupLayout(stock);
        stock.setLayout(stockLayout);
        stockLayout.setHorizontalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLayout.createSequentialGroup()
                .addGap(319, 319, 319)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(318, Short.MAX_VALUE))
        );
        stockLayout.setVerticalGroup(
            stockLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(stockLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(111, Short.MAX_VALUE))
        );

        mainView.addTab("stock", stock);

        jLabel5.setText("Report");

        javax.swing.GroupLayout reportLayout = new javax.swing.GroupLayout(report);
        report.setLayout(reportLayout);
        reportLayout.setHorizontalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportLayout.createSequentialGroup()
                .addGap(327, 327, 327)
                .addComponent(jLabel5)
                .addContainerGap(438, Short.MAX_VALUE))
        );
        reportLayout.setVerticalGroup(
            reportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(reportLayout.createSequentialGroup()
                .addGap(203, 203, 203)
                .addComponent(jLabel5)
                .addContainerGap(286, Short.MAX_VALUE))
        );

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

    }//GEN-LAST:event_btnSalesProductMouseClicked

    private void btnStockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnStockMouseClicked
        // TODO add your handling code here:
        mainView.setSelectedIndex(2);
    }//GEN-LAST:event_btnStockMouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        mainView.setSelectedIndex(3);
    }//GEN-LAST:event_jButton4MouseClicked

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

    }//GEN-LAST:event_tblProductViewMouseClicked

    private void btnProductDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductDeleteMouseClicked
        // TODO add your handling code here:

        deleteProduct();
    }//GEN-LAST:event_btnProductDeleteMouseClicked

    private void btnProductEditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnProductEditMouseClicked
        // TODO add your handling code here:
        editProduct();
        
    }//GEN-LAST:event_btnProductEditMouseClicked

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
    private javax.swing.JButton btnSalesProduct;
    private javax.swing.JButton btnStock;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane mainView;
    private javax.swing.JPanel report;
    private javax.swing.JPanel sales;
    private javax.swing.JPanel stock;
    private javax.swing.JTable tblProductView;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSalesPrice;
    private javax.swing.JTextField txtTotalPrice;
    private javax.swing.JTextField txtUnitPrice;
    // End of variables declaration//GEN-END:variables
}
