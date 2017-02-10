package cn.zhangxd.platform.system.provider.thirdapi;

import java.util.List;

/**
 * The type Yahoo rate bean.
 *
 * @author zhangxd
 */
public class YahooRateBean {

    /**
     * List Bean
     */
    private ListBean list;

    /**
     * Gets list.
     *
     * @return the list
     */
    public ListBean getList() {
        return list;
    }

    /**
     * Sets list.
     *
     * @param list the list
     */
    public void setList(ListBean list) {
        this.list = list;
    }

    /**
     * The type List bean.
     */
    public static class ListBean {

        /**
         * Meta
         */
        private MetaBean meta;
        /**
         * resources
         */
        private List<ResourcesBean> resources;

        /**
         * Gets meta.
         *
         * @return the meta
         */
        public MetaBean getMeta() {
            return meta;
        }

        /**
         * Sets meta.
         *
         * @param meta the meta
         */
        public void setMeta(MetaBean meta) {
            this.meta = meta;
        }

        /**
         * Gets resources.
         *
         * @return the resources
         */
        public List<ResourcesBean> getResources() {
            return resources;
        }

        /**
         * Sets resources.
         *
         * @param resources the resources
         */
        public void setResources(List<ResourcesBean> resources) {
            this.resources = resources;
        }

        /**
         * The type Meta bean.
         */
        public static class MetaBean {
            /**
             * type : resource-list
             * start : 0
             * count : 188
             */
            private String type;
            /**
             * start
             */
            private int start;
            /**
             * count
             */
            private int count;

            /**
             * Gets type.
             *
             * @return the type
             */
            public String getType() {
                return type;
            }

            /**
             * Sets type.
             *
             * @param type the type
             */
            public void setType(String type) {
                this.type = type;
            }

            /**
             * Gets start.
             *
             * @return the start
             */
            public int getStart() {
                return start;
            }

            /**
             * Sets start.
             *
             * @param start the start
             */
            public void setStart(int start) {
                this.start = start;
            }

            /**
             * Gets count.
             *
             * @return the count
             */
            public int getCount() {
                return count;
            }

            /**
             * Sets count.
             *
             * @param count the count
             */
            public void setCount(int count) {
                this.count = count;
            }
        }

        /**
         * The type Resources bean.
         */
        public static class ResourcesBean {
            /**
             * resource : {"classname":"Quote","fields":{"name":"USD/KRW","price":"1164.650024","symbol":"KRW=X","ts":"1481595879","type":"currency","utctime":"2016-12-13T02:24:39+0000","volume":"0"}}
             */

            private ResourceBean resource;

            /**
             * Gets resource.
             *
             * @return the resource
             */
            public ResourceBean getResource() {
                return resource;
            }

            /**
             * Sets resource.
             *
             * @param resource the resource
             */
            public void setResource(ResourceBean resource) {
                this.resource = resource;
            }

            /**
             * The type Resource bean.
             */
            public static class ResourceBean {
                /**
                 * classname : Quote
                 * fields : {"name":"USD/KRW","price":"1164.650024","symbol":"KRW=X","ts":"1481595879","type":"currency","utctime":"2016-12-13T02:24:39+0000","volume":"0"}
                 */
                private String classname;
                /**
                 * fields
                 */
                private FieldsBean fields;

                /**
                 * Gets classname.
                 *
                 * @return the classname
                 */
                public String getClassname() {
                    return classname;
                }

                /**
                 * Sets classname.
                 *
                 * @param classname the classname
                 */
                public void setClassname(String classname) {
                    this.classname = classname;
                }

                /**
                 * Gets fields.
                 *
                 * @return the fields
                 */
                public FieldsBean getFields() {
                    return fields;
                }

                /**
                 * Sets fields.
                 *
                 * @param fields the fields
                 */
                public void setFields(FieldsBean fields) {
                    this.fields = fields;
                }

                /**
                 * The type Fields bean.
                 */
                public static class FieldsBean {
                    /**
                     * name : USD/KRW
                     * price : 1164.650024
                     * symbol : KRW=X
                     * ts : 1481595879
                     * type : currency
                     * utctime : 2016-12-13T02:24:39+0000
                     * volume : 0
                     */
                    private String name;
                    /**
                     * price
                     */
                    private String price;
                    /**
                     * symbol
                     */
                    private String symbol;
                    /**
                     * ts
                     */
                    private String ts;
                    /**
                     * type
                     */
                    private String type;
                    /**
                     * UTC time
                     */
                    private String utctime;
                    /**
                     * volume
                     */
                    private String volume;

                    /**
                     * Gets name.
                     *
                     * @return the name
                     */
                    public String getName() {
                        return name;
                    }

                    /**
                     * Sets name.
                     *
                     * @param name the name
                     */
                    public void setName(String name) {
                        this.name = name;
                    }

                    /**
                     * Gets price.
                     *
                     * @return the price
                     */
                    public String getPrice() {
                        return price;
                    }

                    /**
                     * Sets price.
                     *
                     * @param price the price
                     */
                    public void setPrice(String price) {
                        this.price = price;
                    }

                    /**
                     * Gets symbol.
                     *
                     * @return the symbol
                     */
                    public String getSymbol() {
                        return symbol;
                    }

                    /**
                     * Sets symbol.
                     *
                     * @param symbol the symbol
                     */
                    public void setSymbol(String symbol) {
                        this.symbol = symbol;
                    }

                    /**
                     * Gets ts.
                     *
                     * @return the ts
                     */
                    public String getTs() {
                        return ts;
                    }

                    /**
                     * Sets ts.
                     *
                     * @param ts the ts
                     */
                    public void setTs(String ts) {
                        this.ts = ts;
                    }

                    /**
                     * Gets type.
                     *
                     * @return the type
                     */
                    public String getType() {
                        return type;
                    }

                    /**
                     * Sets type.
                     *
                     * @param type the type
                     */
                    public void setType(String type) {
                        this.type = type;
                    }

                    /**
                     * Gets utctime.
                     *
                     * @return the utctime
                     */
                    public String getUtctime() {
                        return utctime;
                    }

                    /**
                     * Sets utctime.
                     *
                     * @param utctime the utctime
                     */
                    public void setUtctime(String utctime) {
                        this.utctime = utctime;
                    }

                    /**
                     * Gets volume.
                     *
                     * @return the volume
                     */
                    public String getVolume() {
                        return volume;
                    }

                    /**
                     * Sets volume.
                     *
                     * @param volume the volume
                     */
                    public void setVolume(String volume) {
                        this.volume = volume;
                    }
                }
            }
        }
    }
}
