/*-
 * Copyright (C) 2013-2014 The JBromo Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jbromo.common.test.arquillian;

import java.util.Set;

import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.descriptor.api.beans10.BeansDescriptor;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jbromo.common.SetUtil;
import org.jbromo.common.cdi.interceptor.TransactionalInterceptor;
import org.jbromo.common.test.cdi.CdiRunner;

/**
 * Define utility class for Arquillian Test.
 * @author qjafcunuas
 */
public final class ArquillianUtil {

    /**
     * Default constructor.
     */
    private ArquillianUtil() {
        super();
    }

    /**
     * Creates a new jar archive of the specified type. The archive will be backed by the default without beans.xml file.
     * @param classToTest the class to test.
     * @param packageToLoad the package to load into the archive.
     * @return the archive to deploy.
     */
    private static JavaArchive createJar(final Class<?> classToTest, final Package... packageToLoad) {
        return ShrinkWrap.create(JavaArchive.class, classToTest.getSimpleName() + ".jar").addPackages(true, packageToLoad)
                .deleteClasses(CdiRunner.class, TransactionalInterceptor.class);
    }

    /**
     * Creates a new web archive of the specified type. The archive will be backed by the default without beans.xml file.
     * @param classToTest the class to test.
     * @return the archive to deploy.
     */
    private static WebArchive createWar(final Class<?> classToTest) {
        return ShrinkWrap.create(WebArchive.class, classToTest.getSimpleName() + ".war");
    }

    /**
     * Creates a new jar archive of the specified type. The archive will be be backed by the default.
     * @param classToTest the class to test.
     * @param beans the beans descriptor.
     * @param packageToLoad the package to load into the archive.
     * @return the archive to deploy.
     */
    public static JavaArchive createJar(final Class<?> classToTest, final BeansDescriptor beans, final Package... packageToLoad) {
        final JavaArchive arch = createJar(classToTest, packageToLoad);
        setBeansDescriptor(beans, arch);
        return arch;
    }

    /**
     * Creates a new web archive of the specified type. The archive will be be backed by the default.
     * @param classToTest the class to test.
     * @param beans the beans descriptor.
     * @param packageToLoad the package to load into the archive.
     * @return the archive to deploy.
     */
    public static WebArchive createWar(final Class<?> classToTest, final BeansDescriptor beans, final Package... packageToLoad) {
        final WebArchive arch = createWar(classToTest);
        loadRuntimeAndTestDependencies(arch);
        //        arch.addAsLibraries(createJar(classToTest, packageToLoad));
        arch.addPackages(true, packageToLoad).deleteClass(TransactionalInterceptor.class);
        setBeansDescriptor(beans, arch);
        return arch;
    }

    /**
     * Creates a new jar archive of the specified type. The archive will be be backed by the default
     * @param classToTest the class to test.
     * @param beans the beans descriptor.
     * @param packageToLoad the package to load into the archive.
     * @return the archive to deploy.
     */
    public static JavaArchive createTestDependenciesJar(final Class<?> classToTest, final BeansDescriptor beans, final Package... packageToLoad) {
        final JavaArchive arch = createJar(classToTest, null, packageToLoad);
        loadRuntimeAndTestDependencies(arch);
        setBeansDescriptor(beans, arch);
        return arch;
    }

    /**
     * Set beans.xml file into archive.
     * @param beans the beans to set. if null, set an empty file.
     * @param arch the archive to set file.
     */
    private static void setBeansDescriptor(final BeansDescriptor beans, final JavaArchive arch) {
        if (beans == null) {
            arch.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        } else {
            arch.addAsManifestResource(new StringAsset(beans.exportAsString()), "beans.xml");
        }
    }

    /**
     * Set beans.xml file into archive.
     * @param beans the beans to set. if null, set an empty file.
     * @param arch the archive to set file.
     */
    private static void setBeansDescriptor(final BeansDescriptor beans, final WebArchive arch) {
        if (beans == null) {
            arch.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        } else {
            arch.addAsWebInfResource(new StringAsset(beans.exportAsString()), "beans.xml");
        }
    }

    /**
     * Load runtime and test dependencies libraries into an archive.
     * @param arch the archive to add dependencies libraries.
     */
    private static void loadRuntimeAndTestDependencies(final JavaArchive arch) {
        final Set<JavaArchive> libs = loadRuntimeAndTestDependencies();
        // Add classes to archive.
        for (final JavaArchive one : libs) {
            for (final ArchivePath path : one.getContent().keySet()) {
                if (path.get().endsWith(".class")) {
                    arch.addClass(path.get().replace("/", ".").substring(1, path.get().indexOf(".class")));
                } else if (path.get().startsWith("/META-INF/services/")) {
                    arch.add(one.get(path).getAsset(), path.get());
                }
            }
        }
        arch.deleteClass(CdiRunner.class);
    }

    /**
     * Load runtime and test dependencies libraries into an archive.
     * @param arch the archive to add dependencies libraries.
     */
    private static void loadRuntimeAndTestDependencies(final WebArchive arch) {
        arch.addAsLibraries(loadRuntimeAndTestDependencies());
    }

    /**
     * Return runtime and test dependencies.
     * @return archives.
     */
    private static Set<JavaArchive> loadRuntimeAndTestDependencies() {
        final Set<JavaArchive> libs = SetUtil.toSet();
        final PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml");
        // Add compile and runtime dependencies, except jbromo lib.
        for (final JavaArchive one : pom.importCompileAndRuntimeDependencies().resolve().withTransitivity()
                .as(JavaArchive.class)) {
            if (!one.getName().contains("jbromo")) {
                libs.add(one);
            }
        }
        // Add jbromo dependencies.
        for (final JavaArchive one : pom.importRuntimeAndTestDependencies().resolve().withTransitivity()
                .as(JavaArchive.class)) {
            if (one.getName().contains("jbromo")) {
                libs.add(one);
            }
        }
        return libs;
    }
}
