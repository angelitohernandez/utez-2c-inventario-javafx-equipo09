package com.example.inventarioequipo09;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final String ARCHIVO = "inventario.txt";

    public void guardar(List<Producto> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARCHIVO))) {
            for (Producto p : lista) pw.println(p.toString());
        } catch (IOException e) { e.printStackTrace(); }
    }

}
