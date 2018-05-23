package lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.remote;

import java.util.List;

import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponseUsuario;
import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponseBebida;
import lab3.gr05_20181.compumovil.udea.edu.co.lab3servicios.data.ResponsePlato;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.FormUrlEncoded;

import retrofit2.http.POST;

public interface APIService {

    @GET("api/Usuarios")
    Call<List<ResponseUsuario>> getUsers();

    @POST("api/Usuarios")
    @FormUrlEncoded
    Call<ResponseUsuario> postUser(@Field("nombre") String nombre,
                                   @Field("apellido") String apellido,
                                   @Field("correo") String correo,
                                   @Field("contrasena") String contrasena,
                                   @Field("foto") String foto);

    @GET("api/Bebidas")
    Call<List<ResponseBebida>> getBebidas();

    @POST("api/Bebidas")
    @FormUrlEncoded
    Call<ResponseBebida> postBebida(@Field("nombre") String nombre,
                                    @Field("ingredientes") String ingredientes,
                                    @Field("precio") Integer precio,
                                    @Field("foto") String foto);

    @GET("api/Platos")
    Call<List<ResponsePlato>> getPlatos();

    @POST("api/Platos")
    @FormUrlEncoded
    Call<ResponsePlato> postPlato(@Field("nombre") String nombre,
                                  @Field("ingredientes") String ingredientes,
                                  @Field("horario") String horario,
                                  @Field("tipo") String tipo,
                                  @Field("tiempo") String tiempo,
                                  @Field("foto") String foto,
                                  @Field("precio") Integer precio);

}
