package com.lucasbandeira.icompras.pedidos.model.controller.mappers;

import com.lucasbandeira.icompras.pedidos.model.ItemPedido;
import com.lucasbandeira.icompras.pedidos.model.controller.dto.ItemPedidoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {

    ItemPedido map(ItemPedidoDTO dto);
}
