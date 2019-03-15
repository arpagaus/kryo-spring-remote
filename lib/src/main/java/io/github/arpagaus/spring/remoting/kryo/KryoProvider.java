package io.github.arpagaus.spring.remoting.kryo;

import com.esotericsoftware.kryo.Kryo;

@FunctionalInterface
public interface KryoProvider {

    Kryo get();

    static KryoProvider threadLocalProvider(final KryoProvider provider) {
        return new KryoProvider() {

            private final ThreadLocal<KryoProvider> threadLocal = ThreadLocal.<KryoProvider>withInitial(() -> provider::get);

            @Override
            public Kryo get() {
                return threadLocal.get().get();
            }
        };

    }
}
