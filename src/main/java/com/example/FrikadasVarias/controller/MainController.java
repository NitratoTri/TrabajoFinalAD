package com.example.FrikadasVarias.controller;

import com.example.FrikadasVarias.dto.UserDto;
import com.example.FrikadasVarias.entity.*;
import com.example.FrikadasVarias.repository.*;

import com.example.FrikadasVarias.service.UserService;

import com.example.FrikadasVarias.service.impl.CestaImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;



@Controller
public class MainController {

    private UserService userService;
    @Autowired
    private ProductoRepository productoRepo;
    @Autowired
   private ComentarioRepository comentarioRepo;
    @Autowired
    CestaRepository cestaRepo;
    @Autowired
    CestaImpl cestaImpl;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MesaRepository mesaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;


    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    public String home(Model model, @RequestParam (defaultValue = "0") int page, HttpServletRequest request){
        Pageable pageable = PageRequest.of(page, 9);
        List<User> users = userRepository.findAll();
        Page<Producto> productos = productoRepo.findAll(pageable);
        for (User user : users) {
            Cesta cesta = new Cesta();;
            if(user.getCesta()==null){
                cesta.setUser(user);
                user.setCesta(cesta);
                cestaRepo.save(cesta);
                userRepository.save(user);
            }else {
                cesta = user.getCesta();
            }
        }
        model.addAttribute("users", users);
        model.addAttribute("productos", productos);

        String requestedWith = request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(requestedWith)) {
            // Devuelve solo el fragmento de productos y paginación
            return "fragments/productos :: productosYpaginacion";
        }
        return "index";
    }
    @GetMapping("/aboutus")
    public String aboutus(){
        return "aboutus";
    }

    @GetMapping("/error")
    public String error(){
        return "Error";
    }
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/cesta")
    public String cesta(Model model, Authentication auth) {

        // Obtén el email del usuario autenticado
        User user = userService.findByEmail(auth.getName());
        //Conseguir la cesta del usuario
        Cesta cesta = user.getCesta();
        // Busca los productos en la cesta del usuario
        List<Producto> productos = productoRepo.findByCestas_User_Email(user.getEmail());
        cesta.setProductos(productos);
        // Calcula el subtotal de los productos en la cesta
        double subtotal = productos.stream().mapToDouble(Producto::getPrecio).sum();
        cesta.setPrecioTotal(subtotal);
        cestaRepo.save(cesta);
        // Agrega los datos al modelo para pasarlos a la vista
        model.addAttribute("cesta", cesta.getProductos());
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("user", user);
        return "cesta"; // Nombre del archivo HTML (cesta.html)
    }

    @GetMapping("/reservarmesa")
    public String reservarMesa(Model model, Authentication auth){
        if (auth == null) {
            model.addAttribute("mensaje", "Debes registrarte para reservar una mesa.");
            return "errorNoLogin";
        }
        User user = userService.findByEmail(auth.getName());
        List<Mesa> mesas = mesaRepository.findAll();

        model.addAttribute("mesas", mesas);
        model.addAttribute("user", user);

        return "reservarMesa";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin(){
        return "/logout";
    }

    @PostMapping("/logout")
    public String logout(){
        return "/logout";
    }

    @GetMapping("/listaproductos")
    public String listaProductos(Model model){
        List<Producto> productos = productoRepo.findAll();
        List<Categoria> categorias= categoriaRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "productos";
    }


    @GetMapping("/verproducto/{id}")
    public String verProducto(Model model, @PathVariable Long id) {
        System.out.println("Accediendo al producto con ID: " + id);
        Producto producto = productoRepo.findById(id).orElse(null);
        if (producto == null) {
            System.out.println("Producto no encontrado");
            return "redirect:/"; // Redirigir a una página de error personalizada
        }
        model.addAttribute("producto", producto);
        Comentario comentario = new Comentario();
        comentario.setDate(Date.valueOf(LocalDate.now()));
        comentario.setProducto(producto);
        model.addAttribute("comentario", comentario);
        model.addAttribute("comentarios", comentarioRepo.findByProducto(producto));
        return "verproducto";
    }




    // handler method to handle user registration request
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

//Metodo post de añadir al carrito
@PostMapping("/añadircarrito")
@ResponseBody
public ResponseEntity<String> añadirCarrito(@RequestParam("id") Long id, Authentication auth) {
    String email = auth.getName();
    Producto producto = productoRepo.findById(id).orElse(null);
    if (producto!= null) {
        cestaImpl.addProductoToCesta(email, producto);

        return ResponseEntity.ok("Producto añadido correctamente a la cesta");
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado");
}



    @GetMapping("/verproducto")
    public String buscarProductoPorNombre(@RequestParam String nombre, Model model) {
        Producto producto = productoRepo.findByNombre(nombre);
        if (producto == null) {
            model.addAttribute("mensaje", "Producto no encontrado");
            return "errorProducto"; // Página de error personalizada
        }
        model.addAttribute("producto", producto);
        return "redirect:/verproducto/"+producto.getId(); // Página de detalles del producto
    }

}
