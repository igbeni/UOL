package br.com.igbeni.uol.data.entity.mapper;

public interface DataMapper<D, S> {
    D transform(S s);
}
