package ru.kpfu.itis.gnt.registration.config.viewresolver;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CustomViewResolver extends InternalResourceViewResolver {
    private final ArrayList<Integer> supportedCodes;
    private static final String STATUS_PREFIX = "status:";
    private @NonNull String prefix;
    private @NonNull String suffix;

    private CustomViewResolver(Builder builder) {
        prefix = builder.prefix;
        suffix = builder.suffix;
        super.setPrefix(prefix);
        super.setSuffix(suffix);
        supportedCodes = new ArrayList<>(builder.supportedCodes);
    }

    @Override
    public View resolveViewName(@NonNull String viewName, @NonNull Locale locale) throws Exception {
        if (viewName.startsWith(STATUS_PREFIX)) {
            int status;
            try {
                status = Integer.parseInt(viewName.substring(STATUS_PREFIX.length()));
                if (!supportedCodes.contains(status)) {
                    throw new IllegalArgumentException("Status code is not supported: " + viewName.substring(STATUS_PREFIX.length()));
                }
                InternalResourceView view = new InternalResourceView();
                view.setUrl(prefix + status + suffix);
                return this.applyLifecycleMethods(String.valueOf(status), view);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid status code: " + viewName.substring(STATUS_PREFIX.length()), e);
            }
        }
        return super.resolveViewName(viewName, locale);
    }

    public boolean removeStatusCodeSupport(int code) {
        return supportedCodes.remove((Object) code);
    }

    public static Prefix builder() {
        return new Builder();
    }

    public interface Prefix {
        Suffix prefix(String prefix);
    }
    public interface Suffix {
        Build suffix(String suffix);

    }

    public interface Build {
        /**
         * @param statusCode Status code that this View Resolver will support.
         *                   Notice, that file with appropriate name should be created.
         * @return builder so that more status codes can be added.
         */
        Build addStatusCodeSupport(int statusCode);

        Build enableAllHttpStatusCodesSupport(Boolean isEnabled);

        Build setRedirectContextRelative(Boolean isEnabled);

        Build setViewClass(Class<?> viewClass);

        CustomViewResolver build();
    }

    private static class Builder implements Build, Suffix, Prefix {
        private String prefix;
        private String suffix;
        private List<Integer> supportedCodes;
        private Class<?> viewClass;
        private boolean setRedirectContextRelative = false;

        @Override
        public Build addStatusCodeSupport(int statusCode) {
            supportedCodes.add(statusCode);
            return this;
        }

        @Override
        public Build enableAllHttpStatusCodesSupport(Boolean isEnabled) {
            if (isEnabled) {
                supportedCodes.addAll(getHttpStatusCodes());
            } else {
                supportedCodes.removeAll(getHttpStatusCodes());
            }
            return this;
        }

        @Override
        public Build setRedirectContextRelative(Boolean isEnabled) {
            this.setRedirectContextRelative = isEnabled;
            return this;
        }

        @Override
        public Build setViewClass(Class<?> viewClass) {
            this.viewClass = viewClass;
            return this;
        }

        @Override
        public Build suffix(String suffix) {
            supportedCodes = new ArrayList<>();
            this.suffix = suffix;
            return this;
        }

        @Override
        public Suffix prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        @Override
        public CustomViewResolver build() {
            CustomViewResolver viewResolver = new CustomViewResolver(this);
            if (viewClass != null) {
                viewResolver.setViewClass(viewClass);
            }
            viewResolver.setRedirectContextRelative(setRedirectContextRelative);
            return viewResolver;
        }

        private static List<Integer> getHttpStatusCodes() {
            return Arrays.stream(HttpStatus.values()).map(HttpStatus::value).collect(Collectors.toList());
        }

    }
}
