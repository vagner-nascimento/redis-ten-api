package com.vn.controllers;

import com.vn.infrastructure.cache.redis.RedisClientTen;
import com.vn.util.TryParse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.AbstractMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/")
public class RedisController {

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            value = "/{key}")
    @ResponseBody
    public ResponseEntity<String> Set(@RequestBody String value, @PathVariable("key") Object key) {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.Set(key, value)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            value = "/{key}/{expiration}")
    @ResponseBody
    public ResponseEntity<String> Setex(@RequestBody String value,
                                        @PathVariable("key") Object key,
                                        @PathVariable("expiration") Long expiration) {
        if (expiration == null || expiration <= 0)
            return new ResponseEntity<>("Expiration must by greater than 0", HttpStatus.BAD_REQUEST);

        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.SetEX(key, value, expiration)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/{key}")
    @ResponseBody
    public ResponseEntity<String> Get(@PathVariable("key") Object key) {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.Get(key)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/{key}")
    @ResponseBody
    public ResponseEntity<String> Del(@PathVariable("key") String key) {
        try (RedisClientTen redis = new RedisClientTen()) {
            key = URLDecoder.decode(key, "UTF-8");
            String[] keys = key.split("\\s");
            return new ResponseEntity<>(String.valueOf(redis.Del(keys)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/dbsize")
    @ResponseBody
    public ResponseEntity<String> DbSize() {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.DbSize()), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/incr/{key}")
    @ResponseBody
    public ResponseEntity<String> Incr(@PathVariable("key") Object key) {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.Incr(key)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT,
            produces = MediaType.TEXT_PLAIN_VALUE,
            consumes = MediaType.TEXT_PLAIN_VALUE,
            value = "/zadd/{key}")
    @ResponseBody
    public ResponseEntity<String> Zadd(@RequestBody String value, @PathVariable("key") Object key) {
        try {
            value = URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        String[] values = value.split("\\s");
        Map.Entry<Double, Object>[] scoredValues = new Map.Entry[values.length];
        Double score = null;

        for (int i = 0; i < values.length; i++) {
            if (i % 2 == 0) { // even are scores and odd values
                score = TryParse.toDouble(values[i].replace(",", ""));

                if (score == null)
                    return new ResponseEntity<>("Invalid score informed", HttpStatus.BAD_REQUEST);
            } else {
                scoredValues[i] = new AbstractMap.SimpleEntry(score, values[i].toString());
                System.out.println("score: " + score);
                System.out.println("value: " + values[i]);
            }
        }

        //TODO ver porque esta caceta não está preenchendo direto o array.
        for (Map.Entry<Double, Object> e : scoredValues) {
            System.out.println("score: " + e.getKey());
            System.out.println("value: " + e.getValue());
        }

        System.out.println("key: " + key);

        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.ZAdd(key, scoredValues)), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/zcard/{key}")
    @ResponseBody
    public ResponseEntity<String> Zcard(@PathVariable("key") Object key) {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.ZCard(key)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/zrank/{key}/{value}")
    @ResponseBody
    public ResponseEntity<String> Zrank(@PathVariable("value") Object value, @PathVariable("key") Object key) {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.ZRank(key, value)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE,
            value = "/zrange/{key}/{start}/{stop}")
    @ResponseBody
    public ResponseEntity<String> Zrange(@PathVariable("key") Object key,
                                         @PathVariable("start") Long start,
                                         @PathVariable("start") Long stop) {
        try (RedisClientTen redis = new RedisClientTen()) {
            return new ResponseEntity<>(String.valueOf(redis.ZRange(key, start, stop, false)), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Sorry, something wrong happen", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
