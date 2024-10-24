package com.miportafolio.gestion_usuarios.repositories;

import com.miportafolio.gestion_usuarios.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
}
