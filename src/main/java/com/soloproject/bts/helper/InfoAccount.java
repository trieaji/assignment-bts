package com.soloproject.bts.helper;

import com.soloproject.bts.entity.Users;
import com.soloproject.bts.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
// Kegunaannya untuk mendapatkan informasi lengkap tentang pengguna (user)
// yang sedang masuk (login) ke aplikasi Anda, langsung dari database.
public class InfoAccount {

    @Autowired
    UsersRepository usersRepository;


    public Users get(){

        var auth =  SecurityContextHolder.getContext().getAuthentication(); // Mendapatkan informasi otentikasi (siapa yang sedang login) dari Spring Security
        var name = auth.getName(); // Dari informasi otentikasi, mengambil "nama" pengguna yang sedang login. Dalam banyak kasus, ini adalah username atau email.
        var data = usersRepository.findByEmail(name); // Menggunakan "nama" (misalnya email) tadi untuk mencari data pengguna lengkap di database

        if(data.isEmpty()){
            return null;
        }

        return data.get(); // Jika pengguna ditemukan, kembalikan objek Users lengkapnya

    }
}
