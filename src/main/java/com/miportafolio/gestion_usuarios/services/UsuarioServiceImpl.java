package com.miportafolio.gestion_usuarios.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miportafolio.gestion_usuarios.models.Usuario;
import com.miportafolio.gestion_usuarios.repositories.UsuarioRepository;

import java.util.*;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Usuario saveUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getUsuarios(){
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> getUsuariosById(Long id){
        return usuarioRepository.findById(id);
    }

    @Override
    public void deleteUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

}
