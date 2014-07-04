package com.techcavern.wavetact.thread;

import com.techcavern.wavetact.annot.CMD;
import com.techcavern.wavetact.objects.Command;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public final class CommandCollection
        implements Callable<List<Command>> {
    private final String start_pkg;

    public CommandCollection(String pkg) {
        this.start_pkg = pkg;
    }

    @Override
    public List<Command> call()
            throws Exception {
        List<Command> commands = new LinkedList<>();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = loader.getResources(this.start_pkg.replace('.', '/'));
        while (resources.hasMoreElements()) {
            commands.addAll(this.findCommands(new File(resources.nextElement().getFile()), this.start_pkg));
        }

        return commands;
    }

    private List<Command> findCommands(File dir, String pkgName)
            throws Exception {
        List<Command> commands = new LinkedList<>();
        if (!dir.exists()) {
            return commands;
        }

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                commands.addAll(this.findCommands(file, pkgName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                commands.add(this.newCommand(Class.forName(pkgName + "." + file.getName().substring(0, file.getName().length() - 6))));
            }
        }

        return commands;
    }

    private Command newCommand(Class<?> clazz)
            throws Exception {
        for (Constructor<?> c : clazz.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(CMD.class)) {
                if (c.getParameterTypes().length > 0) {
                    throw new InvalidCommandConstructorException(clazz, c);
                } else {
                    c.setAccessible(true);
                    return (Command) c.newInstance();
                }
            }
        }

        throw new InvalidCommandException(clazz);
    }

    private static final class InvalidCommandConstructorException
            extends Exception {
        public InvalidCommandConstructorException(Class<?> c, Constructor<?> constructor) {
            super("Constructor " + c.getName() + "#" + constructor.getName() + "(" + parameterString(constructor) + ") is invalid");
        }

        private static String parameterString(Constructor<?> c) {
            StringBuilder builder = new StringBuilder();

            Class<?>[] params = c.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                builder.append(params[i].getSimpleName());

                if (i < params.length - 1) {
                    builder.append(",");
                }
            }

            return builder.toString();
        }
    }

    private final class InvalidCommandException
            extends Exception {
        public InvalidCommandException(Class<?> c) {
            super("Class " + c.getName() + " is invalid for a command");
        }
    }
}