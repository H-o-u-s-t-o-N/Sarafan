package HoustoN.Sarafan.util;

import java.util.Objects;

@FunctionalInterface
public interface CustomConsumer<U, E, P> {

        void accept(U u, E e, P l);

        default CustomConsumer<U, E, P> andThen(CustomConsumer<? super U, ? super E, ? super P> after) {
            Objects.requireNonNull(after);

            return (u, e, p) -> {
                accept(u, e, p);
                after.accept(u, e, p);
            };
        }
}
