package ru.kpfu.itis.gnt.hwpebble.bloomfilter;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kpfu.itis.gnt.hwpebble.bloomfilter.BloomModel;
import ru.kpfu.itis.gnt.hwpebble.constants.ViewPaths;
import ru.kpfu.itis.gnt.hwpebble.model.PostDto;

import java.io.IOException;
import java.util.Arrays;

@Controller
@RequestMapping("/bloom")
public class BloomController {

    private static final String BLOOM_ITEM_KEY = "bloom";
    @GetMapping
    public String newBloom(ModelMap map) {
        map.put(BLOOM_ITEM_KEY, new BloomModel());
        map.put("originalText", TextHolder.text);
        return "bloom";
    }

    @PostMapping
    public String newPostCreate(
            RedirectAttributes redirectAttributes,
            @ModelAttribute(BLOOM_ITEM_KEY)  BloomModel bloom,
            BloomFilterImpl<Object> bloomFilter
    ) {
        redirectAttributes.addFlashAttribute("message",  String.valueOf(bloomFilter.mightContain(bloom.word)));
        return "redirect:" + MvcUriComponentsBuilder.fromMappingName("BC#newBloom").build();
    }
}
