package br.com.igbeni.uol.data.entity.mapper;

public interface JsonMapper<D, S> {
    D transformFromEntity(S source);
}
