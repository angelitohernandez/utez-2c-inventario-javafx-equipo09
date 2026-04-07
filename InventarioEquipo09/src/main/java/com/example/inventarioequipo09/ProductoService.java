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

    public List<Producto> cargar() {
        List<Producto> lista = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) return lista;
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(",");
                lista.add(new Producto(d[0], d[1], Double.parseDouble(d[2]), Integer.parseInt(d[3]), d[4]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}