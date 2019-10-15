package com.agah.furkan.newsapplicationwithweka.util;

import timber.log.Timber;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.StringToWordVector;

public class WekaOperationsUtil {
    public static StringToWordVector createFilter(Instances instances) {
        StringToWordVector filter = new StringToWordVector();
        try {
            filter.setOptions(weka.core.Utils.splitOptions("-R first-last -W 1000 -prune-rate -1.0 -C -T -N 1 -L -stemmer weka.core.stemmers.LovinsStemmer -stopwords-handler weka.core.stopwords.Null -M 1 -tokenizer \"weka.core.tokenizers.WordTokenizer -delimiters \\\" \\\\r\\\\n\\\\t.,;:\\\\\\'\\\\\\\"()?!\\\"\""));
        } catch (Exception e) {
            Timber.tag("timber").d("filter set options error.");
            e.printStackTrace();
        }
        try {
            filter.setInputFormat(instances);
        } catch (Exception e) {
            e.printStackTrace();
            Timber.tag("timber").d("filter input format error.");
        }
        return filter;
    }
}
