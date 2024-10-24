package com.miportafolio.gestion_usuarios.services;

import java.util.List;
import java.util.Optional;

import com.miportafolio.gestion_usuarios.models.Usuario;
import com.miportafolio.gestion_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario crearUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id){
        return usuarioRepository.findById(id);
    }
    
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
