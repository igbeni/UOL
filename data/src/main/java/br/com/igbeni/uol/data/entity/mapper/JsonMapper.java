package br.com.igbeni.uol.data.entity.mapper;

import java.util.Collection;
import java.util.List;

public interface JsonMapper<D, S> {
    D transformFromEntity(S source);
}
