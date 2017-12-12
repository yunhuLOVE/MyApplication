package com.augur.zongyang.task;

import org.apache.http.HttpException;

import java.util.HashMap;

/**
 * Created by yunhu on 2017-12-11.
 */

public class TaskParams {

        private HashMap<String, Object> params = null;

        public TaskParams() {
            params = new HashMap<String, Object>();
        }

        public TaskParams(String key, Object value) {
            this();
            put(key, value);
        }

        public void put(String key, Object value) {
            params.put(key, value);
        }

        public Object get(String key) {
            return params.get(key);
        }

        public boolean getBoolean(String key) throws HttpException {
            Object object = get(key);
            if (object.equals(Boolean.FALSE)
                    || (object instanceof String && ((String) object)
                    .equalsIgnoreCase("false"))) {
                return false;
            } else if (object.equals(Boolean.TRUE)
                    || (object instanceof String && ((String) object)
                    .equalsIgnoreCase("true"))) {
                return true;
            }
            throw new HttpException(key + " is not a Boolean.");
        }

        public double getDouble(String key) throws HttpException {
            Object object = get(key);
            try {
                return object instanceof Number ? ((Number) object).doubleValue()
                        : Double.parseDouble((String) object);
            } catch (Exception e) {
                throw new HttpException(key + " is not a number.");
            }
        }

        public int getInt(String key) throws HttpException {
            Object object = get(key);
            try {
                return object instanceof Number ? ((Number) object).intValue()
                        : Integer.parseInt((String) object);
            } catch (Exception e) {
                throw new HttpException(key + " is not an int.");
            }
        }
        public String getString(String key) throws HttpException {
            Object object = get(key);
            return object == null ? null : object.toString();
        }

        public boolean has(String key) {
            return this.params.containsKey(key);
        }


}
