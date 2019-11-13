package ru.alfastrah.library.log.demoserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SomeService {
    Holder transform(Holder holder) {
        holder.setValue(holder.getValue().replace("body", "value"));
        return holder;
    }
}
