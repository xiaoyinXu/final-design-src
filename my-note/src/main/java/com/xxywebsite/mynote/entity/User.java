package com.xxywebsite.mynote.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails, Serializable {

   @Id
   private int id;
   private String username;
   private String password;

   List<Menu> menus;

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
//      SimpleGrantedAuthority admin = new SimpleGrantedAuthority("admin");
//      List<GrantedAuthority> a = new ArrayList<>();
//      a.add(admin);
//      return a;
      return null;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
