package com.miportafolio.gestion_usuarios.services;

import com.miportafolio.gestion_usuarios.models.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    Usuario saveUsuario(Usuario usuario);
    Usuario updateUsuario(Usuario usuario);

    List<Usuario> getUsuarios();
    Optional<Usuario> getUsuariosById(Long id);
    
    void deleteUsuario(Long id);
  
    
    // @Autowired
    // private UsuarioRepository usuarioRepository;

    // public Usuario crearUsuario(Usuario usuario){
    //     return usuarioRepository.save(usuario);
    // }

    // public Optional<Usuario> obtenerUsuarioPorId(Long id){
    //     return usuarioRepository.findById(id);
    // }
    
    // public List<Usuario> obtenerTodosLosUsuarios() {
    //     return usuarioRepository.findAll();
    // }

    // public void eliminarUsuario(Long id) {
    //     usuarioRepository.deleteById(id);
    // }
}
