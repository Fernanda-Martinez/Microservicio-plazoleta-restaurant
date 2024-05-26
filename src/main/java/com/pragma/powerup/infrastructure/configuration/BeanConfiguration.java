package com.pragma.powerup.infrastructure.configuration;


import com.pragma.powerup.domain.api.*;
import com.pragma.powerup.domain.spi.*;
import com.pragma.powerup.domain.usecase.*;
import com.pragma.powerup.infrastructure.http.HttpRequest;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PedidoJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RestauranteJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IPlatoEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRestauranteEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.*;
import com.pragma.powerup.infrastructure.out.jpa.adapter.PlatoJpaAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestauranteEntityMapper restauranteEntityMapper;
    private final IPlatoRepository platoRepository;
    private final IPlatoEntityMapper platoEntityMapper;
    private final IPedidoRepository pedidoRepository;
    private final IPlatoPedidoRepository platoPedidoRepository;
    private final ICategoriaRepository categoriaRepository;

    @Bean
    public IHttpRequestPersistencePort httpRequestPersistencePort(){
        return new HttpRequest();
    }

    @Bean
    public IHttpRequestServicePort httpRequestServicePort(){
        return new HttpRequestUseCase(httpRequestPersistencePort());
    }

    //Restaurante
    @Bean
    public IRestaurantePersistencePort restaurantePersistencePort() {
        return new RestauranteJpaAdapter(restaurantRepository, restauranteEntityMapper);
    }

    @Bean
    public IRestauranteServicePort restauranteServicePort() {

        return new RestauranteUseCase(restaurantePersistencePort());
    }

    //Plato
    @Bean
    public IPlatoPersistencePort platoPersistencePort() {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper, categoriaRepository, restaurantRepository);
    }

    @Bean
    public IPlatoServicePort platoServicePort() {

        return new PlatoUseCase(platoPersistencePort());
    }

    //Modificar Plato
    @Bean
    public IPlatoModPersistencePort platoModPersistencePort () {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper, categoriaRepository, restaurantRepository);
    }

    @Bean
    public IPlatoModServicePort platoModServicePort() {

        return new PlatoModificadoUseCase(platoModPersistencePort());
    }

    //Cambiar estado Plato
    @Bean
    public ICambiarEstadoPlatoPersistencePort cambiarEstadoPlatoPersistencePort () {
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper, categoriaRepository, restaurantRepository);
    }

    @Bean
    public ICambiarEstadoPlatoServicePort cambiarEstadoPlatoServicePort() {
        return new CambiarEstadoPlatoUseCase(cambiarEstadoPlatoPersistencePort());
    }

    //Listar restaurantes

    @Bean
    public IListarRestaurantePersistencePort listarRestaurantePersistencePort(){
        return new RestauranteJpaAdapter(restaurantRepository, restauranteEntityMapper);
    }

    @Bean
    public IListarRestauranteServicePort listarRestauranteServicePort(){
        return new ListarRestauranteUseCase(listarRestaurantePersistencePort());
    }

    //Listar platos

    @Bean
    public IListarPlatoPersistencePort listarPlatoPersistencePort(){
        return new PlatoJpaAdapter(platoRepository, platoEntityMapper, categoriaRepository, restaurantRepository);
    }

    @Bean
    public IListarPlatoServicePort listarPlatoServicePort(){
        return new ListarPlatoUseCase(listarPlatoPersistencePort());
    }

    //Realizar Pedido
    @Bean
    public IPedidoPersistencePort pedidoPersistencePort(){
        return new PedidoJpaAdapter(pedidoRepository, platoPedidoRepository, restaurantRepository, platoRepository, restauranteEntityMapper);
    }

    @Bean
    public IPedidoServicePort pedidoServicePort(){
        return new PedidoUseCase(pedidoPersistencePort());
    }

    //ListarPedido
    @Bean
    public IListarPedidoPersistencePort listarPedidoPersistencePort(){
        return new PedidoJpaAdapter(pedidoRepository, platoPedidoRepository, restaurantRepository, platoRepository, restauranteEntityMapper);
    }
    @Bean
    public IListarPedidoServicePort listarPedidoServicePort(){
        return new ListarPedidoUseCase(listarPedidoPersistencePort());
    }

    @Bean
    public IAsignarPedidoPersistencePort asignarPedidoPersistencePort() {
        return new PedidoJpaAdapter(pedidoRepository, platoPedidoRepository, restaurantRepository, platoRepository, restauranteEntityMapper);
    }

    @Bean
    public IAsignarPedidoServicePort asignarPedidoServicePort(){
        return new AsignarPedidoUseCase(asignarPedidoPersistencePort());
    }

    @Bean
    public ICancelarPedidoPersistencePort cancelarPedidoPersistencePort() {
        return new PedidoJpaAdapter(pedidoRepository, platoPedidoRepository, restaurantRepository, platoRepository, restauranteEntityMapper);
    }
    @Bean
    public ICancelarPedidoServicePort cancelarPedidoServicePort() {
        return new CancelarPedidoUseCase(cancelarPedidoPersistencePort());
    }

    @Bean
    public ICambiarEstadoPedidoPersistencePort cambiarEstadoPedidoPersistencePort() {
        return new PedidoJpaAdapter(pedidoRepository, platoPedidoRepository, restaurantRepository, platoRepository, restauranteEntityMapper);
    }
    @Bean
    public ICambiarEstadoPedidoServicePort cambiarEstadoPedidoServicePort() {
        return new CambiarEstadoPedidoUseCase(cambiarEstadoPedidoPersistencePort());
    }

    @Bean
    public ICambiarEntregadoPersistencePort cambiarEntregadoPersistencePort() {
        return new PedidoJpaAdapter(pedidoRepository, platoPedidoRepository, restaurantRepository, platoRepository, restauranteEntityMapper);
    }
    @Bean
    public ICambiarEntregadoServicePort cambiarEntregadoServicePort() {
        return new CambiarEntregadoUseCase(cambiarEntregadoPersistencePort());
    }

}