package br.com.igbeni.uol.mapper;

import java.util.Collection;

public interface DataMapper<D, S> {
    D transform(S s);

    Collection<D> transform(Collection<S> sCollection);
}
