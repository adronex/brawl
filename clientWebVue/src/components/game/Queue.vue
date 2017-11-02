<template>
    <div class="queueWrapper">
        <div class="queue" v-show="queue.length > 0">
            <div class="heroInQueue"
                 v-for="hero in queue">
                {{ hero.id }}
            </div>
        </div>
    </div>
</template>

<script>
    'use strict';
    export default {
        props: {
            queue: {
                type: Array,
                required: true
            }
        }
    }
</script>

<style>
    .queueWrapper {
        display: block;
        width: 640px;
        height: 60px;
        margin: auto;
        text-align: center;
        vertical-align: middle;
        padding-top: 10px;
    }

    .queueWrapper .queue {
        background-color: #ecf7f7;
        height: 100%;
        width: max-content;
        margin: 0 auto;
    }

    .queueWrapper .queue .heroInQueue {
        float: left;
        width: 50px;
        height: 50px;
        margin: 5px;
        background-color: darkgrey;
    }
</style>