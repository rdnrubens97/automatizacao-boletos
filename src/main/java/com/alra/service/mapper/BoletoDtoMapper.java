package com.alra.service.mapper;

import com.alra.service.model.cx230.GeradorBoleto;
import com.alra.service.model.cxempresa.boletodto.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class BoletoDtoMapper {

    private final ModelMapper mapper;

    public BoletoDtoMapper() {
        this.mapper = new ModelMapper();
        configureMappings();
    }

    private void configureMappings() {
        mapper.addMappings(new PropertyMap<GeradorBoleto, BoletoDto>() {
            @Override
            protected void configure() {
                map().setSeuNumero(source.getIdSistema());
                map().setValorNominal(source.getValorSistema());
                map().setDataVencimento(source.getVencimento());
                map().setNumDiasAgenda(60);
                map().getPagador().setCpfCnpj(source.getCpfcliente());
                map().getPagador().setTipoPessoa(source.getTipoPessoa());
                map().getPagador().setNome(source.getNomecliente());
                map().getPagador().setEndereco(source.getRua());
                map().getPagador().setNumero(source.getNumero());
                map().getPagador().setComplemento(source.getComplemento());
                map().getPagador().setBairro(source.getBairro());
                map().getPagador().setCidade(source.getCidade());
                map().getPagador().setUf(source.getUf());
                map().getPagador().setCep(source.getCep());
                map().getPagador().setEmail("");
                map().getPagador().setDdd("");
                map().getPagador().setTelefone("");
                map().setMensagem(source.getDescricao());
                map().setDesconto1(new Desconto1());
                map().setDesconto2(new Desconto2());
                map().setDesconto3(new Desconto3());
                map().setMulta(source.getVencimento());
                map().setMora(new Mora());
                map().setBeneficiarioFinal(new BeneficiarioFinal());
            }
        });
    }

    public BoletoDto mapearParaBoleto(GeradorBoleto geradorBoleto) {
        BoletoDto boletoDto =  mapper.map(geradorBoleto, BoletoDto.class);
        return boletoDto;
    }


}
