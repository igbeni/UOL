package br.com.igbeni.uol.data.entity.mapper;

import java.util.Collection;
import java.util.List;

public interface DataMapper<D, S> {
    D transform(S s);
}
