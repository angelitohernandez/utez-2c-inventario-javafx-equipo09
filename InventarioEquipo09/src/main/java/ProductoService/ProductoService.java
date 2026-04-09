package ProductoService;

import ProductoFile.Producto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoService {
    private final String ARCHIVO = "inventario.txt";

    public Producto Validaciones(String codigo, String nombre, String precioStr, String stockStr, String categoria) throws Exception {
        double precio;
        int stock;
        if (codigo.isEmpty() || nombre.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || categoria.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios.");
        }
        try {
            precio = Double.parseDouble(precioStr);
            stock = Integer.parseInt(stockStr);
        } catch (NumberFormatException e) {
            throw new Exception("El precio y Stock deben ser valores numéricos válidos.");
        }
        if (precio <= 0) {
            throw new Exception("El precio debe ser mayor a 0.");
        }
        if (stock < 0) {
            throw new Exception("El stock no puede ser negativo.");
        }

        return new Producto(codigo, nombre, precio, stock, categoria);
    }


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