---
--- Generated by Luanalysis
--- Created by 86187.
--- DateTime: 2023/4/17 18:30
---
local key = KEYS[1]
local count = tonumber(ARGV[1])
local time = tonumber(ARGV[2])
local current = redis.call('get', key)
--首先判断current是否存在，因为可能是第一次访问，key不存在也就拿不到对应的value，只能拿到一个null，在判断访问次数current是否大于限流次数count
if current and tonumber(current) > count then
    return tonumber(current)
end
current = redis.call('incr', key)
--如果获取到的current次数为1，则说明是第一次访问，并设置key的过期时间
if tonumber(current) == 1 then
    redis.call('expire', key, time)
end
--返回自增后的current
return tonumber(current)