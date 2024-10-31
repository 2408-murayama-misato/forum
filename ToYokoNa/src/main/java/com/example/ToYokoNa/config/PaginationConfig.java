package com.example.ToYokoNa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration //すべてのコントローラに対して共通処理を行うアノテーション
public class PaginationConfig implements WebMvcConfigurer {

    // 設定を補完する情報のことをリゾルバ(resolver)と呼ぶ
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

        // Pageableに対して設定を行うためのクラスであり、リゾルバ
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

        // ページ単位に表示する件数を追加(第一引数：ページ番号、第二引数：１ページあたりの表示件数)
        resolver.setFallbackPageable(PageRequest.of(0,2));

        // 具体的な設定をリゾルバに追加後、リストに追加
        argumentResolvers.add(resolver);

    }
}
