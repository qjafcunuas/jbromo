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
package org.jbromo.dao.jpa.query.jpql.from;

import org.jbromo.common.BooleanUtil;
import org.jbromo.sample.server.model.src.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit JpqlFromBuilder class.
 * @author qjafcunuas
 */
public class JpqlFromBuilderTest {

    /**
     * Define the beginning of the from query.
     */
    private static final String ROOT_FROM_QUERY = "from " + User.class.getName() + " o ";

    /**
     * Define the root alias.
     */
    private static final String ROOT_ALIAS = "o";

    /**
     * Validate from builder.
     * @param from the from builder to validate.
     * @param fromQuery the waited from query
     */
    private void validate(final JpqlFromBuilder from, final String fromQuery) {
        Assert.assertEquals(from.toString(), fromQuery);
        final StringBuilder builder = new StringBuilder("select o ");
        from.build(builder);
        Assert.assertEquals(builder.toString(), "select o " + fromQuery);
    }

    /**
     * Return a new instance of from query.
     * @return the new instance.
     */
    private JpqlFromBuilder newInstance() {
        return new JpqlFromBuilder(User.class);
    }

    /**
     * Test constructor.
     */
    @Test
    public void constructor() {
        final JpqlFromBuilder from = newInstance();
        validate(from, ROOT_FROM_QUERY);
    }

    /**
     * Test getRootAlias method.
     */
    @Test
    public void getRootAlias() {
        Assert.assertNotNull(new JpqlFromBuilder(User.class).getRootAlias());
        Assert.assertEquals(new JpqlFromBuilder(User.class).getRootAlias(), ROOT_ALIAS);
    }

    /**
     * Test join method.
     */
    @Test
    public void join() {
        String query = ROOT_FROM_QUERY;
        String alias;
        int aliasId = 1;
        final JpqlFromBuilder from = newInstance();

        for (final Boolean newAlias : BooleanUtil.ALL) {
            aliasId = 1;
            // alias o1
            alias = from.join(from.getRootAlias(), "manyToOneGroup", newAlias);
            query += "inner join o.manyToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToOneGroup");
            }
            validate(from, query);

            // alias o2
            alias = from.join(from.getRootAlias(), "oneToOneGroup", newAlias);
            query += "inner join o.oneToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.oneToOneGroup");
            }
            validate(from, query);

            // alias o3
            alias = from.join(from.getRootAlias(), "manyToManyGroups", newAlias);
            query += "inner join o.manyToManyGroups ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToManyGroups");
            }
            validate(from, query);

            // alias o4
            alias = from.join(from.getRootAlias(), "surnames", newAlias);
            query += "inner join o.surnames ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.surnames");
            }
            validate(from, query);
        }

    }

    /**
     * Test joinFetch method.
     */
    @Test
    public void joinFetch() {
        String query = ROOT_FROM_QUERY;
        String alias;
        int aliasId = 1;
        final JpqlFromBuilder from = newInstance();

        for (final Boolean newAlias : BooleanUtil.ALL) {
            aliasId = 1;
            // alias o1
            alias = from.joinFetch(from.getRootAlias(), "manyToOneGroup", newAlias);
            query += "inner join fetch o.manyToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToOneGroup");
            }
            validate(from, query);

            // alias o2
            alias = from.joinFetch(from.getRootAlias(), "oneToOneGroup", newAlias);
            query += "inner join fetch o.oneToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.oneToOneGroup");
            }
            validate(from, query);

            // alias o3
            alias = from.joinFetch(from.getRootAlias(), "manyToManyGroups", newAlias);
            query += "inner join fetch o.manyToManyGroups ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToManyGroups");
            }
            validate(from, query);

            // alias o4
            alias = from.joinFetch(from.getRootAlias(), "surnames", newAlias);
            query += "inner join fetch o.surnames ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.surnames");
            }
            validate(from, query);
        }

    }

    /**
     * Test rightJoin method.
     */
    @Test
    public void rightJoin() {
        String query = ROOT_FROM_QUERY;
        String alias;
        int aliasId = 1;
        final JpqlFromBuilder from = newInstance();

        for (final Boolean newAlias : BooleanUtil.ALL) {
            aliasId = 1;
            // alias o1
            alias = from.rightJoin(from.getRootAlias(), "manyToOneGroup", newAlias);
            query += "right join o.manyToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToOneGroup");
            }
            validate(from, query);

            // alias o2
            alias = from.rightJoin(from.getRootAlias(), "oneToOneGroup", newAlias);
            query += "right join o.oneToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.oneToOneGroup");
            }
            validate(from, query);

            // alias o3
            alias = from.rightJoin(from.getRootAlias(), "manyToManyGroups", newAlias);
            query += "right join o.manyToManyGroups ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToManyGroups");
            }
            validate(from, query);

            // alias o4
            alias = from.rightJoin(from.getRootAlias(), "surnames", newAlias);
            query += "right join o.surnames ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.surnames");
            }
            validate(from, query);
        }

    }

    /**
     * Test rightJoinFetch method.
     */
    @Test
    public void rightJoinFetch() {
        String query = ROOT_FROM_QUERY;
        String alias;
        int aliasId = 1;
        final JpqlFromBuilder from = newInstance();

        for (final Boolean newAlias : BooleanUtil.ALL) {
            aliasId = 1;
            // alias o1
            alias = from.rightJoinFetch(from.getRootAlias(), "manyToOneGroup", newAlias);
            query += "right join fetch o.manyToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToOneGroup");
            }
            validate(from, query);

            // alias o2
            alias = from.rightJoinFetch(from.getRootAlias(), "oneToOneGroup", newAlias);
            query += "right join fetch o.oneToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.oneToOneGroup");
            }
            validate(from, query);

            // alias o3
            alias = from.rightJoinFetch(from.getRootAlias(), "manyToManyGroups", newAlias);
            query += "right join fetch o.manyToManyGroups ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToManyGroups");
            }
            validate(from, query);

            // alias o4
            alias = from.rightJoinFetch(from.getRootAlias(), "surnames", newAlias);
            query += "right join fetch o.surnames ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.surnames");
            }
            validate(from, query);
        }

    }

    /**
     * Test leftJoin method.
     */
    @Test
    public void leftJoin() {
        String query = ROOT_FROM_QUERY;
        String alias;
        int aliasId = 1;
        final JpqlFromBuilder from = newInstance();

        for (final Boolean newAlias : BooleanUtil.ALL) {
            aliasId = 1;
            // alias o1
            alias = from.leftJoin(from.getRootAlias(), "manyToOneGroup", newAlias);
            query += "left join o.manyToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToOneGroup");
            }
            validate(from, query);

            // alias o2
            alias = from.leftJoin(from.getRootAlias(), "oneToOneGroup", newAlias);
            query += "left join o.oneToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.oneToOneGroup");
            }
            validate(from, query);

            // alias o3
            alias = from.leftJoin(from.getRootAlias(), "manyToManyGroups", newAlias);
            query += "left join o.manyToManyGroups ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToManyGroups");
            }
            validate(from, query);

            // alias o4
            alias = from.leftJoin(from.getRootAlias(), "surnames", newAlias);
            query += "left join o.surnames ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.surnames");
            }
            validate(from, query);
        }

    }

    /**
     * Test leftJoinFetch method.
     */
    @Test
    public void leftJoinFetch() {
        String query = ROOT_FROM_QUERY;
        String alias;
        int aliasId = 1;
        final JpqlFromBuilder from = newInstance();

        for (final Boolean newAlias : BooleanUtil.ALL) {
            aliasId = 1;
            // alias o1
            alias = from.leftJoinFetch(from.getRootAlias(), "manyToOneGroup", newAlias);
            query += "left join fetch o.manyToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToOneGroup");
            }
            validate(from, query);

            // alias o2
            alias = from.leftJoinFetch(from.getRootAlias(), "oneToOneGroup", newAlias);
            query += "left join fetch o.oneToOneGroup ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.oneToOneGroup");
            }
            validate(from, query);

            // alias o3
            alias = from.leftJoinFetch(from.getRootAlias(), "manyToManyGroups", newAlias);
            query += "left join fetch o.manyToManyGroups ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.manyToManyGroups");
            }
            validate(from, query);

            // alias o4
            alias = from.leftJoinFetch(from.getRootAlias(), "surnames", newAlias);
            query += "left join fetch o.surnames ";
            if (newAlias) {
                Assert.assertEquals(alias, ROOT_ALIAS + aliasId++);
                query += alias + " ";
            } else {
                Assert.assertEquals(alias, "o.surnames");
            }
            validate(from, query);
        }
    }

}
