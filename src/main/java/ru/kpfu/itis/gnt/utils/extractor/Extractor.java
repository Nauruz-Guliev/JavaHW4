package ru.kpfu.itis.gnt.utils.extractor;

public interface Extractor<Result, From> {
    Result extract(From value) throws  Exception;
}
