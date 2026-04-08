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

}