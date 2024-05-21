package com.crca.apirest.apirest.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.crca.apirest.apirest.Entities.Producto;
import com.crca.apirest.apirest.Repositories.ProductoRepository;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired //instanciacion del repositorio 
    private ProductoRepository productoRepository; //es conexion de base de datos

    @GetMapping
    public List<Producto> obtenerProductos(){ //devuelve lista de productos lo trar de Producto
        return productoRepository.findAll();// taer todos los repositorios
    }

    @GetMapping("/{id}") //reciba en la barra ID, se recupera el producto por el ID
    public Producto obtenerProductoPorId(@PathVariable Long id){
        return productoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No ha sido encontrado el producto con el ID: " + id)); //si no se encuentra el ID, se lanza un error. Devuelve el producto tal cual lo encontro
    }


    @PostMapping //agregar nuevo recurso. Devuleve uno solo
    public Producto crearProducto(@RequestBody Producto producto){ //este body caracteristicas obligatorias en la identidad. Requestbody  peticion del cliente al servidor
        return productoRepository.save(producto); //Se esta guardan en la BD y devulve producto mas 1 en el id 
        
    }

    @PutMapping("/{id}") 
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto){  //desde aqui se obtiene el producto que se encontro
        Producto producto = productoRepository.findById(id) //se asigna en una variable, producto de tipo producto
        .orElseThrow(() -> new RuntimeException("No ha sido encontrado el producto con el ID: " + id)); 

        producto.setNombre(detallesProducto.getNombre()); //para modificar esos atributos, nombre, descripcion, marca, precio 
        producto.setDescripcion(detallesProducto.getDescripcion());
        producto.setMarca(detallesProducto.getMarca());
        producto.setPrecio(detallesProducto.getPrecio());

        return productoRepository.save(producto); //se manda el producto actualizado en la BD
    }

    @DeleteMapping("/{id}")
    public String poductoEliminado(@PathVariable Long id){
        Producto producto = productoRepository.findById(id) //se busca por el ID
        .orElseThrow(() -> new RuntimeException("No ha sido encontrado el producto con el ID: " + id)); 

        productoRepository.delete(producto);
        return "El producto con el ID: " + id + " fue eliminado correctamente!"; //aqui se borra
    } 



}
