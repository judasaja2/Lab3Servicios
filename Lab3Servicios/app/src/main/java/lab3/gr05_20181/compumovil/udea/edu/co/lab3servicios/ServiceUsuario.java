package lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponseUsuario;
import retrofit2.http.POST;

public interface ServiceUsuario {

    @GET("api/Usuarios")
    Call<List<ResponseUsuario>> getUsers();

    @POST("api/Usuarios")
    Call<List<ResponseUsuario>> postUsers();

}
