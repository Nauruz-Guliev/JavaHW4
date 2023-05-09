package ru.kpfu.itis.gnt.utils.mapper;

public interface Mapper<To, From> {
    To map(From value) throws Exception;
}
