package com.yihuo.bihai.newex.utils.uniform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisConnectionException;
/**
 * @author <a href="mailto:dragonjackielee@163.com">李智龙</a>
 * @since 2018/10/30 上午11:40
 */
public class UniformJedisUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniformJedisUtil.class);
    
    private static UniformJedisUtil jedisUtil;
    private static JedisPool pool = null;

    private UniformJedisUtil() {
    }


    public static synchronized UniformJedisUtil getInstance(String host, String redisPasswd) {
        if (jedisUtil != null) {
            return jedisUtil;
        } else {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(10);
            config.setMaxIdle(10);
            config.setMinIdle(1);
            config.setMaxWaitMillis(10000L);
            config.setTestOnBorrow(false);
            pool = new JedisPool(config, host, 6379, 2000, redisPasswd);
            jedisUtil = new UniformJedisUtil();
            return jedisUtil;
        }
    }

    public void incr(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.incr(key);
        } catch (JedisConnectionException var7) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            LOGGER.error("Could not get a resource from the pool : {}", var7);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public long scard(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            long var3 = jedis.scard(key);
            return var3;
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            LOGGER.error("Could not get a resource from the pool : {}", var8);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return 0L;
    }

    public void sadd(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.sadd(key, new String[]{value});
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            LOGGER.error("Could not get a resource from the pool : {}", var8);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public boolean contiansId(String key, String userid) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            boolean var4 = jedis.sismember(key, userid);
            return var4;
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            LOGGER.error("Could not get a resource from the pool : {}", var8);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return false;
    }

    public List<Boolean> contiansId(String userid) {
        Jedis jedis = null;
        ArrayList contiansList = new ArrayList();

        try {
            jedis = pool.getResource();
            Pipeline p = jedis.pipelined();
            Response<Boolean> isMember = p.sismember("pushserver1_ids", userid);
            Response<Boolean> isMember2 = p.sismember("pushserver2_ids", userid);
            Response<Boolean> isMember3 = p.sismember("pushserver3_ids", userid);
            Response<Boolean> isMember4 = p.sismember("pushserver4_ids", userid);
            Response<Boolean> isMember5 = p.sismember("pushserver5_ids", userid);
            p.sync();
            contiansList.add(isMember.get());
            contiansList.add(isMember2.get());
            contiansList.add(isMember3.get());
            contiansList.add(isMember4.get());
            contiansList.add(isMember5.get());
            ArrayList var10 = contiansList;
            return var10;
        } catch (JedisConnectionException var14) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return contiansList;
    }

    public void pushinfo(String key, String data) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.rpush(key, new String[]{data});
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public void publish(String channel, String data) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.publish(channel, data);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public void subscribe(String channel, JedisPubSub jedisPubSub) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.subscribe(jedisPubSub, new String[]{channel});
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    public void setInfo(String key, String data) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.set(key, data);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public void del(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.del(key);
        } catch (JedisConnectionException var7) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public String getInfo(String key) {
        Jedis jedis = null;
        String result = "";

        try {
            jedis = pool.getResource();
            result = jedis.get(key);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return result;
    }

    public void pushKline(String key, String data) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            Long len = jedis.rpush(key, new String[]{data});
            if (len > 500L) {
                jedis.ltrim(key, len - 500L, -1L);
            }
        } catch (JedisConnectionException var9) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public String lpop(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            String var3 = jedis.lpop(key);
            return var3;
        } catch (JedisConnectionException var7) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public List<String> lrange(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            long keyLength = jedis.llen(key);
            List<String> userPasses = jedis.lrange(key, 0L, keyLength);
            List var6 = userPasses;
            return var6;
        } catch (JedisConnectionException var10) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            var10.printStackTrace();
            LOGGER.error(" Jredis  lrange error ", var10);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public List<String> lrangeAndRemove(String key, int maxValue) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            long keyLength = jedis.llen(key);
            if (keyLength > (long)maxValue) {
                keyLength = (long)maxValue;
            }

            List<String> result = jedis.lrange(key, 0L, keyLength);
            jedis.ltrim(key, 0L, keyLength);
            List var7 = result;
            return var7;
        } catch (JedisConnectionException var11) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            LOGGER.error(" Jredis  lrangeAndRemove error ", var11);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public List<String> lrange(String key, int start, int end) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            List var5 = jedis.lrange(key, (long)start, (long)end);
            return var5;
        } catch (JedisConnectionException var9) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            var9.printStackTrace();
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public String ltrim(String key, int start, int end) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            String var5 = jedis.ltrim(key, (long)start, (long)end);
            return var5;
        } catch (JedisConnectionException var9) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            var9.printStackTrace();
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return "";
    }

    public List<String> blpop(int timeOut, String... key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            List var4 = jedis.blpop(timeOut, key);
            return var4;
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public long llen(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            long var3 = jedis.llen(key);
            return var3;
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return 0L;
    }

    public void sadd(String channel, String... message) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.sadd(channel, message);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public Set<String> smembers(String channel) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            Set var3 = jedis.smembers(channel);
            return var3;
        } catch (JedisConnectionException var7) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public void srem(String channel, Set<String> set) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.srem(channel, (String[])set.toArray(new String[set.size()]));
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public void hmset(String channel, Map<String, String> params) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hmset(channel, params);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public Map<String, String> hgetAll(String channel) {
        Map<String, String> result = null;
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            result = jedis.hgetAll(channel);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return result;
    }

    public boolean exists(String channel) {
        Jedis jedis = null;
        boolean falg = false;

        try {
            jedis = pool.getResource();
            falg = jedis.exists(channel);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return falg;
    }

    public String hget(String channel, String field) {
        Jedis jedis = null;
        String result = null;

        try {
            jedis = pool.getResource();
            result = jedis.hget(channel, field);
        } catch (JedisConnectionException var9) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return result;
    }

    public void expire(String channel, int time) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.expire(channel, time);
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public List<String> lrangeAndRemove(String key) {
        Jedis jedis = null;

        try {
            List<String> result = null;
            jedis = pool.getResource();
            long keyLength = jedis.llen(key);
            Transaction tx = jedis.multi();
            Response<List<String>> resultRespone = tx.lrange(key, 0L, keyLength);
            tx.ltrim(key, keyLength, -1L);
            tx.exec();
            result = (List)resultRespone.get();
            List var8 = result;
            return var8;
        } catch (JedisConnectionException var12) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }

            LOGGER.error(" Jredis  lrangeAndRemove error ", var12);
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

        return null;
    }

    public void lrem(String key, long count, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.lrem(key, count, value);
        } catch (JedisConnectionException var10) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public void hset(String key, String field, String value) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hset(key, field, value);
        } catch (JedisConnectionException var9) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }

    public void hdel(String key, String field) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            jedis.hdel(key, new String[]{field});
        } catch (JedisConnectionException var8) {
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            if (null != jedis) {
                pool.returnResource(jedis);
            }

        }

    }
}
