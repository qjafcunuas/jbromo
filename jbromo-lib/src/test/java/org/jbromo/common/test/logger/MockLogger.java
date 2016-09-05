package org.jbromo.common.test.logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.jbromo.common.ArrayUtil;
import org.jbromo.common.ClassUtil;
import org.jbromo.common.ListUtil;
import org.jbromo.common.invocation.InvocationException;
import org.jbromo.common.invocation.InvocationUtil;
import org.jbromo.common.invocation.InvocationUtil.AccessType;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

@Slf4j
public class MockLogger implements Logger {

    /**
     * serial version UID.
     */
    private static final long serialVersionUID = -4014219155627802093L;

    /**
     * The SLF4J logger.
     */
    private final Logger logger;

    /**
     * If true, logs are logged by SLF4J, else not.
     */
    @Getter
    @Setter
    private boolean enabledSlf4jLog = true;

    /**
     * Define logged messages, by level.
     */
    @Getter
    private final List<LoggedMessage> logged = ListUtil.toList();

    /**
     * Define a logged message.
     */
    @Getter
    @AllArgsConstructor
    private class LoggedMessage {
        /**
         * The level.
         */
        private final int level;

        /**
         * The message.
         */
        private final String message;

        /**
         * Message's arguments.
         */
        private final Object[] arguments;

        /**
         * The throwable.
         */
        private final Throwable throwable;
    }

    /**
     * Default constructor.
     * @param clazz the clazz logging.
     */
    public MockLogger(final Class<?> clazz) {
        super();
        this.logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * Inject a mocker logger into the class.
     * @param clazz the class to inject logger.
     * @return the injected logger.
     */
    public static MockLogger mock(final Class<?> clazz) {
        final Field field = InvocationUtil.getField(clazz, Logger.class);
        // Set field not final.
        try {
            final Field modifiersField = Field.class.getDeclaredField("modifiers");
            Assert.assertNotNull(modifiersField);
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            final Logger clazzLogger = InvocationUtil.getValue(null, field, AccessType.FIELD, false);
            if (!ClassUtil.isInstance(clazzLogger, MockLogger.class)) {
                final MockLogger mock = new MockLogger(clazz);
                InvocationUtil.injectValue(null, field, mock);
                // Set mock in clazz.
                return mock;
            } else {
                return MockLogger.class.cast(clazzLogger);
            }
        } catch (final Exception e) {
            log.error("Cannot mock logger", e);
            Assert.fail(e.getMessage());
            return null;
        }
    }

    /**
     * Inject the slf4j logger into the class.
     * @param clazz the class to inject logger.
     */
    public static void unmock(final Class<?> clazz) {
        final Field field = InvocationUtil.getField(clazz, Logger.class);
        // SLF4J logger.
        final Logger logger = LoggerFactory.getLogger(clazz);
        try {
            // Load mocked logger.
            final Logger mock = InvocationUtil.getValue(null, field, AccessType.FIELD, false);
            if (ClassUtil.isInstance(mock, MockLogger.class)) {
                // Set slf4j logger in class.
                InvocationUtil.injectValue(null, field, logger);
            }
        } catch (final InvocationException e) {
            Assert.fail(e.getMessage());
        }
    }

    /**
     * Return enabled level.
     */
    public int getLevel() {
        if (this.logger.isTraceEnabled()) {
            return LocationAwareLogger.TRACE_INT;
        } else if (this.logger.isDebugEnabled()) {
            return LocationAwareLogger.DEBUG_INT;
        } else if (this.logger.isInfoEnabled()) {
            return LocationAwareLogger.INFO_INT;
        } else if (this.logger.isWarnEnabled()) {
            return LocationAwareLogger.WARN_INT;
        } else if (this.logger.isErrorEnabled()) {
            return LocationAwareLogger.ERROR_INT;
        } else {
            Assert.fail("Not implemented!");
            return -1;
        }
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    /**
     * Log message.
     * @param level the log level.
     * @param message the message.
     */
    private void log(final int level, final String message) {
        log(level, message, new Object[0]);
    }

    /**
     * Log message.
     * @param level the log level.
     * @param message the message.
     * @param arguments the message arguments.
     */
    private void log(final int level, final String message, final Object... arguments) {
        log(level, message, (Throwable) null, arguments);
    }

    /**
     * Log message.
     * @param level the log level.
     * @param message the message.
     * @param throwable the exception to log.
     * @param arguments the message arguments.
     */
    private void log(final int level, final String message, final Throwable throwable, final Object... arguments) {
        this.logged.add(new LoggedMessage(level, message, arguments, throwable));
        if (isEnabledSlf4jLog()) {
            Object[] args;
            if (throwable == null) {
                args = arguments;
            } else {
                args = ArrayUtil.toArray(arguments, throwable);
            }
            switch (level) {
                case LocationAwareLogger.TRACE_INT:
                    this.logger.trace(message, args);
                    break;
                case LocationAwareLogger.DEBUG_INT:
                    this.logger.debug(message, args);
                    break;
                case LocationAwareLogger.INFO_INT:
                    this.logger.info(message, args);
                    break;
                case LocationAwareLogger.WARN_INT:
                    this.logger.warn(message, args);
                    break;
                case LocationAwareLogger.ERROR_INT:
                    this.logger.error(message, args);
                    break;
                default:
                    Assert.fail("Not implemented for level " + level);
            }
        }
    }

    @Override
    public void trace(final String msg) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, msg);
        }
    }

    @Override
    public void trace(final String format, final Object arg) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, format, arg);
        }
    }

    @Override
    public void trace(final String format, final Object arg1, final Object arg2) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, format, arg1, arg2);
        }
    }

    @Override
    public void trace(final String format, final Object[] argArray) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, format, argArray);
        }
    }

    @Override
    public void trace(final String msg, final Throwable throwable) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, msg, throwable);
        }
    }

    @Override
    public boolean isTraceEnabled(final Marker marker) {
        return this.logger.isTraceEnabled(marker);
    }

    @Override
    public void trace(final Marker marker, final String msg) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, msg);
        }
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, format, arg);
        }
    }

    @Override
    public void trace(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, format, arg1, arg2);
        }
    }

    @Override
    public void trace(final Marker marker, final String format, final Object[] argArray) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, format, argArray);
        }
    }

    @Override
    public void trace(final Marker marker, final String msg, final Throwable throwable) {
        if (isTraceEnabled()) {
            log(LocationAwareLogger.TRACE_INT, msg, throwable);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    public void debug(final String msg) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, msg);
        }
    }

    @Override
    public void debug(final String format, final Object arg) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, format, arg);
        }
    }

    @Override
    public void debug(final String format, final Object arg1, final Object arg2) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, format, arg1, arg2);
        }
    }

    @Override
    public void debug(final String format, final Object[] argArray) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, format, argArray);
        }
    }

    @Override
    public void debug(final String msg, final Throwable throwable) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, msg, throwable);
        }
    }

    @Override
    public boolean isDebugEnabled(final Marker marker) {
        return this.logger.isDebugEnabled(marker);
    }

    @Override
    public void debug(final Marker marker, final String msg) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, msg);
        }
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, format, arg);
        }
    }

    @Override
    public void debug(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, format, arg1, arg2);
        }
    }

    @Override
    public void debug(final Marker marker, final String format, final Object[] argArray) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, format, argArray);
        }
    }

    @Override
    public void debug(final Marker marker, final String msg, final Throwable throwable) {
        if (isDebugEnabled()) {
            log(LocationAwareLogger.DEBUG_INT, msg, throwable);
        }
    }

    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    @Override
    public void info(final String msg) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, msg);
        }
    }

    @Override
    public void info(final String format, final Object arg) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, format, arg);
        }
    }

    @Override
    public void info(final String format, final Object arg1, final Object arg2) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, format, arg1, arg2);
        }
    }

    @Override
    public void info(final String format, final Object[] argArray) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, format, argArray);
        }
    }

    @Override
    public void info(final String msg, final Throwable throwable) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, msg, throwable);
        }
    }

    @Override
    public boolean isInfoEnabled(final Marker marker) {
        return this.logger.isInfoEnabled(marker);
    }

    @Override
    public void info(final Marker marker, final String msg) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, msg);
        }
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, format, arg);
        }
    }

    @Override
    public void info(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, format, arg1, arg2);
        }
    }

    @Override
    public void info(final Marker marker, final String format, final Object[] argArray) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, format, argArray);
        }
    }

    @Override
    public void info(final Marker marker, final String msg, final Throwable t) {
        if (isInfoEnabled()) {
            log(LocationAwareLogger.INFO_INT, msg, t);
        }
    }

    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    @Override
    public void warn(final String msg) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, msg);
        }
    }

    @Override
    public void warn(final String format, final Object arg) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, format, arg);
        }
    }

    @Override
    public void warn(final String format, final Object[] argArray) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, format, argArray);
        }
    }

    @Override
    public void warn(final String format, final Object arg1, final Object arg2) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, format, arg1, arg2);
        }
    }

    @Override
    public void warn(final String msg, final Throwable throwable) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, msg, throwable);
        }
    }

    @Override
    public boolean isWarnEnabled(final Marker marker) {
        return this.logger.isWarnEnabled(marker);
    }

    @Override
    public void warn(final Marker marker, final String msg) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, msg);
        }
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, format, arg);
        }
    }

    @Override
    public void warn(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, format, arg1, arg2);
        }
    }

    @Override
    public void warn(final Marker marker, final String format, final Object[] argArray) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, format, argArray);
        }
    }

    @Override
    public void warn(final Marker marker, final String msg, final Throwable throwable) {
        if (isWarnEnabled()) {
            log(LocationAwareLogger.WARN_INT, msg, throwable);
        }
    }

    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    @Override
    public void error(final String msg) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, msg);
        }
    }

    @Override
    public void error(final String format, final Object arg) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, format, arg);
        }
    }

    @Override
    public void error(final String format, final Object arg1, final Object arg2) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, format, arg1, arg2);
        }
    }

    @Override
    public void error(final String format, final Object[] argArray) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, format, argArray);
        }
    }

    @Override
    public void error(final String msg, final Throwable throwable) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, msg, throwable);
        }
    }

    @Override
    public boolean isErrorEnabled(final Marker marker) {
        return this.logger.isErrorEnabled(marker);
    }

    @Override
    public void error(final Marker marker, final String msg) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, msg);
        }
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, format, arg);
        }
    }

    @Override
    public void error(final Marker marker, final String format, final Object arg1, final Object arg2) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, format, arg1, arg2);
        }
    }

    @Override
    public void error(final Marker marker, final String format, final Object[] argArray) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, format, argArray);
        }
    }

    @Override
    public void error(final Marker marker, final String msg, final Throwable throwable) {
        if (isErrorEnabled()) {
            log(LocationAwareLogger.ERROR_INT, msg, throwable);
        }
    }
}
