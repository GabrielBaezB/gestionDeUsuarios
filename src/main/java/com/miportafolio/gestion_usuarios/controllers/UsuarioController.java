package com.miportafolio.gestion_usuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.miportafolio.gestion_usuarios.models.Usuario;
import com.miportafolio.gestion_usuarios.services.UsuarioServiceImpl;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    @Autowired
    UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping
    public ResponseEntity<Usuario> saveUsuario(@RequestBody Usuario usuario){
        try{
            Usuario saveUsuario = usuarioServiceImpl.saveUsuario(usuario);
            return new ResponseEntity<>(saveUsuario, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario){
        try{
            Usuario saveUsuario = usuarioServiceImpl.updateUsuario(usuario);
            return new ResponseEntity<>(saveUsuario, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios(){
        return new ResponseEntity<>(usuarioServiceImpl.getUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuariosById(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioServiceImpl.getUsuariosById(id);

        return usuario.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> 
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioServiceImpl.getUsuariosById(id);
        if(usuario.isPresent()){
            usuarioServiceImpl.deleteUsuario(usuario.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // private UsuarioService usuarioService;

    // @PostMapping
    // public Usuario crearUsuario(@RequestBody Usuario usuario){
    //     return usuarioService.crearUsuario(usuario);
    // }

    // @GetMapping("/{id}")
    // public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id){
    //     Optional<Usuario> usuario =  usuarioService.obtenerUsuarioPorId(id);
    //     return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    // }

    // @GetMapping
    // public List<Usuario> obtenerTodosLosUsuarios() {
    //     return usuarioService.obtenerTodosLosUsuarios();
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
    //     usuarioService.eliminarUsuario(id);
    //     return ResponseEntity.noContent().build();
    // }
    
}
