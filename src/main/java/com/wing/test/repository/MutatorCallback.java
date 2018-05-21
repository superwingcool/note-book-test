package com.wing.test.repository;

import org.apache.hadoop.hbase.client.BufferedMutator;

public interface MutatorCallback {

    void doInMutator(BufferedMutator mutator) throws Exception;
}