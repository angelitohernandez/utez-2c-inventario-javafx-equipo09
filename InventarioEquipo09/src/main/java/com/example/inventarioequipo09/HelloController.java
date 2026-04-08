package com.example.inventarioequipo09;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class HelloController {
    @FXML private TextField txtCodigo, txtNombre, txtPrecio, txtStock, txtCategoria, txtBuscar;
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colCodigo, colNombre, colCategoria;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colStock;

    private ObservableList<Producto> masterData = FXCollections.observableArrayList();
    private ProductoService service = new ProductoService();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        masterData.addAll(service.cargar());

        FilteredList<Producto> filteredData = new FilteredList<>(masterData, p -> true);
        txtBuscar.textProperty().addListener((obs, old, newVal) -> {
            filteredData.setPredicate(p -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return p.getNombre().toLowerCase().contains(newVal.toLowerCase()) || p.getCodigo().contains(newVal);
            });
        });
        tablaProductos.setItems(filteredData);

        tablaProductos.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                txtCodigo.setText(newVal.getCodigo());
                txtCodigo.setEditable(false);
                txtNombre.setText(newVal.getNombre());
                txtPrecio.setText(String.valueOf(newVal.getPrecio()));
                txtStock.setText(String.valueOf(newVal.getStock()));
                txtCategoria.setText(newVal.getCategoria());
            }
        });
    }
    @FXML
    protected void onGuardarClick() {
        try {
            String codigo = txtCodigo.getText();

            boolean yaExiste = masterData.stream()
                    .anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));


            if (txtCodigo.isEditable() && yaExiste) {
                throw new Exception("El código '" + codigo + "' ya está registrado. Use otro.");
            }

            if (!txtCodigo.isEditable()) {
                Producto sel = tablaProductos.getSelectionModel().getSelectedItem();
                if (sel != null) {
                    sel.setNombre(txtNombre.getText());
                    sel.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    sel.setStock(Integer.parseInt(txtStock.getText()));
                    sel.setCategoria(txtCategoria.getText());
                }
            } else {
                masterData.add(new Producto(codigo, txtNombre.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        Integer.parseInt(txtStock.getText()),
                        txtCategoria.getText()));
            }

            service.guardar(masterData);
            tablaProductos.refresh();
            onLimpiarClick();

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error de Validación");
            alert.setHeaderText(null);
            alert.setContentText("Faltan rellenar campos o codigo duplicado.");
            alert.showAndWait();
        }
    }
    @FXML
    protected void onEliminarClick() {
        Producto sel = tablaProductos.getSelectionModel().getSelectedItem();
        if (sel != null) {
            masterData.remove(sel);
            service.guardar(masterData);
            onLimpiarClick();
        }
    }

    @FXML
    protected void onLimpiarClick() {
        txtCodigo.clear(); txtCodigo.setEditable(true);
        txtNombre.clear(); txtPrecio.clear(); txtStock.clear(); txtCategoria.clear();
        tablaProductos.getSelectionModel().clearSelection();
    }

}
