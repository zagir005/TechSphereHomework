package com.zagirlek.remote.extractor

import com.zagirlek.remote.extractor.dto.ExtractedArticleDTO

class MockNewsExtractorSource: RemoteNewsExtractorSource{
    override suspend fun extractArticleText(link: String): Result<ExtractedArticleDTO> =
        Result.success(
            ExtractedArticleDTO(
                link = "link",
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce eu nulla tempus libero tincidunt efficitur. Morbi tristique non tortor eu scelerisque. Aenean molestie mauris velit. Nulla non cursus nisi. Vivamus ex orci, consectetur at consequat id, ullamcorper et turpis. Quisque nec quam arcu. Vestibulum ac posuere justo. Ut nec maximus massa, vitae imperdiet ipsum. Quisque pharetra metus sed magna viverra sagittis. Vestibulum condimentum fermentum urna a ullamcorper. Phasellus varius nunc in odio suscipit, eget porttitor ipsum pharetra. Duis vitae facilisis nibh. " +
                        "Cras nec mauris laoreet, imperdiet enim non, posuere eros. Duis dictum bibendum massa, eget maximus leo feugiat sed. Sed accumsan nunc in porta ornare. Sed ut ligula dui. Vestibulum vitae ante sed tortor imperdiet luctus. Quisque pellentesque aliquet venenatis. Fusce elit lectus, luctus nec lacus et, semper venenatis mauris. Cras tempor semper massa vitae placerat. " +
                        "Suspendisse pharetra id dolor vitae ultrices. Vivamus id nibh in sem dapibus fermentum. Nunc tincidunt arcu ut dolor posuere, quis rhoncus leo ultricies. Maecenas lacinia imperdiet augue, quis finibus augue porttitor in. Aenean laoreet lacus non dolor porta blandit. Sed aliquam ante ut orci mattis, vitae malesuada neque dapibus. Nullam dignissim metus mauris, vitae semper enim dignissim nec. Duis id dapibus elit. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vestibulum rhoncus laoreet sapien, quis lobortis felis malesuada vel. Nam blandit, magna eu commodo posuere, quam sapien faucibus nisl, sit amet imperdiet ex turpis a turpis. Vestibulum ac turpis ac purus iaculis ultrices. Interdum et malesuada fames ac ante ipsum primis in faucibus. " +
                        "Nullam imperdiet, elit eget tempor varius, risus nulla auctor risus, porta suscipit nibh libero eget velit. Praesent tristique massa sed dictum accumsan. Suspendisse blandit a orci sed rutrum. Cras sit amet dictum ante. Pellentesque pulvinar ante ac tortor tincidunt, vel rhoncus felis pulvinar. Vestibulum nec quam ut nulla fringilla feugiat at eu sapien. Morbi quis sem eget risus dapibus varius. Nam ut dictum nibh. Mauris a mi nibh. Donec congue porttitor orci vitae condimentum. " +
                        "Maecenas pretium convallis dolor ut ultrices. In at dui pulvinar, vestibulum tellus quis, consequat dui. Pellentesque eu gravida ligula. Suspendisse blandit sodales massa et varius. Duis convallis feugiat ex. In sed nisl eu velit tincidunt volutpat vel sed nibh. Nunc pharetra sapien eget ante commodo sollicitudin. Aenean pulvinar magna sem, id vulputate diam porta ut. Sed quis tempus ex, at finibus nunc."
            )
        )

}